try {
	if (!window.openDatabase) {
		//alert('not supported');
	} else {
		// setting for our database
		var shortName = 'mydb';
		var version = '1.0';
		var displayName = 'simple db';
		var maxSize = 65536; // in bytes
		var database = openDatabase(shortName, version, displayName, maxSize);

		// You should have a database instance in db.
		console.log("mydb created");
	}
} catch (e) {
	// Error handling code goes here.
	if (e == 2) {
		// Version number mismatch.
		alert("Invalid database version.");
	} else {
		alert("Unknown error " + e + ".");
	}
}

function showError(transaction, error) {
	console.log("error occured: this=" + this);// error.message+"
	// error.code:"+error.code);
	return true;
}

function nullDataHandler(transaction, results) {
	console.log("nullDataHandler=" + this);
}

// Create table
database.transaction(function(transaction) {
	// Create our parnters table if it doesn't exist.
	transaction.executeSql("CREATE TABLE lastSeenPartners ("
			+ "indx INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + // don't use
																	// saved
																	// word
																	// "index"
																	// it makes
																	// problems!!!
			"id INTEGER NOT NULL, photo TEXT NOT NULL, first TEXT NOT NULL);");
	console.log("lastSeenPartners created");

}, nullDataHandler, showError);

// save a partner.
var savePartner = function(id, photo, first) {

	// Insert a new partner.
	database
			.transaction(
					function(transaction) {

						// check for duplication of user
						transaction.executeSql(
								'DELETE FROM lastSeenPartners WHERE id = ?;',
								[ id ]);
						// Insert a new student with the given values.
						transaction
								.executeSql(
										("INSERT INTO lastSeenPartners (id, photo, first) VALUES (?,?,?);"),
										[ id, photo, first ]);
						console.log("sqlite insert success: id=" + id
								+ ", photo=" + photo + ", first=" + first);
						// trim table to store only up to 3 last seen partners
						transaction
								.executeSql(
										'SELECT count(*) as num, min(indx) as minIndex FROM lastSeenPartners;',
										[],
										function(transaction, results) {
											if (results.rows.item(0).num > 3) {
												var idx = results.rows.item(0).minIndex;
												transaction
														.executeSql(
																("DELETE FROM lastSeenPartners WHERE indx = ?;"),
																[ idx ]);
												console
														.log("sqlite delete success");
											}
										});

					}, nullDataHandler, showError);
};

// I get all the partners.
var getPartners = function(callback) {
	// Get all the partners.
	database.transaction(function(transaction) {

		// Get all the partners in the table.
		transaction.executeSql("SELECT * FROM lastSeenPartners ORDER BY indx DESC;", [], function(
				transaction, results) {
					// Return the partners results set.
					callback( results );
				});
	});
	console.log("db select");

};