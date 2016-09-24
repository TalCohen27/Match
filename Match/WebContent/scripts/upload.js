/**
 * 
 */

var file;

$(function() {

	$(':file').change(
			function() {

				var file = this.files[0];
				name = file.name;
				size = file.size;
				type = file.type;
				console.log("upload status: chosen file: name:" + name
						+ " , size:" + size + " , type" + type);

			});

});

$(function() {

	// add a function to the submit event
	$("#imageUpload").submit(function() {

		file = $("#file");
		if ($("#imageUpload").valid()) {
		$(this).ajaxSubmit({

			dataType : 'json',
			error : function() {
				console.log("Failed to submit");
				printUploadError("Failed to submit, please try one more time");
			},
			success : function(data) {
				if (data == "true") {
					self.location = "#page4";
				} else {
					printUploadError(data);
				}

			}
		});
	}
		return false;
	});
});


$(document).on('pageinit', function validator() {
	
    $.validator.addMethod("loginRegex", function(value, element) {
        return this.optional(element) || /^[a-zA-Z0-9\-]+$/i.test(value);
    }, "Username must contain only letters, numbers, or dashes.");
    
    
    $('#imageUpload').validate({
    	
    	
    	
        rules: {
            file: {
                required: true,
            },

        },
        messages: {
            file: { 
	    	required : "Please choose photo."
            },

        }
    });
    
});


function printUploadError(error) {

	$("#uploaderror").empty().text(error);
};

