$(document).ready(function() {
    // When the input is under a change the display div will be modified

    $('#editForm\\:titleInput').on('input', function() {
        var inputValue = $(this).val();
        $('#titleDiv').find('h2').text(inputValue);
    });

    $('#editForm\\:description, #editForm\\:myPart, #editForm\\:learned').on('input', function() {
        $('#resume').html('<h3>Description</h3>' +
            '<p>' + $('#editForm\\:description').val() + '</p>' +
            '<p>' + $('#editForm\\:myPart').val() + '</p>' +
            '<p>' + $('#editForm\\:learned').val() + '</p>');
    });

    $('#editForm\\:image').on('change', function (event) {
        let file = event.target.files[0];
        if (file) {
            $('#projectImage').attr('src', URL.createObjectURL(file));
        }
    });

    $('#editForm\\:technologies').on('input' , function () {
        $('#technologiesDiv').html('<h3>Technologies used :</h3> <p>' + $('#editForm\\:technologies').val() + '</p>');
    });
});
