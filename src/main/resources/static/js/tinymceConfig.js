tinymce.init({
    selector: "#editor",
    plugins:
        "advlist anchor autosave image link lists media searchreplace table template visualblocks wordcount code",
    toolbar:
        "undo redo | styles | bold italic underline strikethrough | align | table link image media pageembed | bullist numlist outdent indent | spellcheckdialog a11ycheck typography code",
    height: 540,
    invalid_elements: 'script',
    a11ychecker_level: "aaa",
    typography_langs: ["en-US"],
    typography_default_lang: "en-US",
    advcode_inline: true,
    content_style: `
        body {
          font-family: 'Roboto', sans-serif;
          color: #222;
        }
        img {
          height: auto;
          margin: auto;
          padding: 10px;
          display: block;
        }
        img.medium {
          max-width: 25%;
        }
        a {
          color: #116B59;
        }
        .related-content {
          padding: 0 10px;
          margin: 0 0 15px 15px;
          background: #eee;
          width: 200px;
          float: right;
        }
      `,


    setup: (editor) => {
        //This is what will send the data to our database
        //Triggered by clicking off tinymce editor
        editor.on('change', (e) => {
            var myData = {
                "contentId": document.getElementById("contentId").value,
                "contentBody": tinymce.get('editor').getContent()
                }
            $.ajax({
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                url: 'savecontent',
                data: JSON.stringify(myData),

                success: function(data){
                }
            })
        })
    },
});
