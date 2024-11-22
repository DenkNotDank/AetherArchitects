tinymce.init({
    selector: "#editor",
    plugins: "advlist anchor autosave image link lists media searchreplace table template visualblocks wordcount code",
    toolbar: "undo redo | styles | bold italic underline strikethrough | align | table link image media pageembed | bullist numlist outdent indent | spellcheckdialog a11ycheck typography code",
    height: 540,
    images_upload_url: '/uploadMedia',
    automatic_uploads: true,
    images_upload_handler: function (blobInfo, success, failure) {
        let formData = new FormData();
        formData.append('file', blobInfo.blob(), blobInfo.filename());
        fetch('/uploadMedia', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else if (response.status === 413) {
                    throw new Error("Error: The file size exceeds the allowed limit.");
                } else {
                    return response.text().then(text => { throw new Error(text); });
                }
            })
            .then(json => success(json.location))
            .catch(error => failure(error.message));
    },

    invalid_elements: 'script',
    a11ychecker_level: "aaa",
    typography_langs: ["en-US"],
    typography_default_lang: "en-US",
    advcode_inline: true,
    content_style: `
        body { font-family: 'Roboto', sans-serif; color: #222; }
        img { height: auto; margin: auto; padding: 10px; display: block; }
        img.medium { max-width: 25%; }
        a { color: #116B59; }
        .related-content { padding: 0 10px; margin: 0 0 15px 15px; background: #eee; width: 200px; float: right; }
    `,
    setup: (editor) => {
        editor.on('change', () => {
            const myData = {
                "contentId": 1,
                "contentBody": editor.getContent()
            };
            $.ajax({
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                url: 'savecontent',
                data: JSON.stringify(myData),
                success: function(data) {
                    console.log("Content saved successfully");
                }
            });
        });
    }
});
