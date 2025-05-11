
function checkFileSize(obj) {
    const maxSize = 20 * 1024 * 1024;
    let objSize = $(obj)[0].files[0].size;
    if(objSize > maxSize) {
        alert('첨부파일 사이즈는 20MB 이내로 등록 가능합니다.');
        $(obj).val("");
        $(obj).focus();
        return false;
    }
    return true;
}

function checkImageSize(obj) {
    const maxSize = 2 * 1024 * 1024;
    let objSize = obj[0].size;
    if(objSize > maxSize) {
        alert('이미지 사이즈는 2MB 이내로 등록 가능합니다.');
        $(obj).val("");
        $(obj).focus();
        return false;
    }
    return true;
}

function uploadSummernoteImageFile(file, type, editor) {
    let data = new FormData();
    data.append("file", file);
    data.append("type", type);
    $.ajax({
        data: data,
        type: "POST",
        url: "/upload/image",
        contentType: false,
        processData: false,
        success: function(data, result, res) {
            if(res.status === 200) {
                let img = $('<img>').attr('src', data.img);
                img.attr('width','100%');
                $(editor).summernote('insertNode', img[0]);

            } else {
                alert('이미지 업로드중 오류가 발생했습니다.');
            }
        },
        error: function (xhr) {
            alert('이미지 업로드중 오류가 발생했습니다.');
        }
    })
}

function initSummernote(obj) {
    $(obj).summernote({
        callbacks: {
            onImageUpload: function (files) {
                if (!files.length) return;
                if (!checkImageSize(files)) return;
                var file = files[0];
                // create FileReader
                var reader = new FileReader();
                reader.onloadend = function () {
                    var img = $("<img>").attr({src: reader.result}); // << Add here img attributes !
                    $('.textarea').summernote("insertNode", img[0]);
                }

                if (file) {
                    // convert fileObject to datauri
                    reader.readAsDataURL(file);
                }
                uploadSummernoteImageFile(files[0], 'img', this);
            }
        },
        lang: "ko-KR",
        placeholder: "내용을 입력해주세요.",
        width: '100%',
        height: 300,
        fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋움체','바탕체'],
        fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','36','48','60'],
        toolbar: [
            ['fontname', ['fontname']],
            ['fontsize', ['fontsize']],
            ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
            ['color', ['forecolor','color']],
            ['table', ['table']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['height', ['height']],
            ['insert',['picture','link']],
            ['view', ['fullscreen', 'help']]
        ]
    });
}

function addFileRow(target, name) {
    let file_idx = 0;
    file_idx ++;
    let fileRow = "<div class='custom-file sub-row' id='inp_file_area" + file_idx + "'>";
    fileRow += "<input type='file' class='custom-file-input file-sub-row' id='customFile" + file_idx + "' name='" + name + "'>";
    fileRow += "<label class='custom-file-label file-sub-row' for='customFile" + file_idx + "'>파일을 선택하세요</label>";
    fileRow += "<button type='button' class='btn btn-danger del-file' onClick='delRow($(this))'><i class='fa fa-minus'></i></button>";
    fileRow += "</div>";

    $(target).append(fileRow);

}

$(".custom-file-input").change(function() {
    if(checkFileSize($(this))) {
        let fileValue = $(this).val().split("\\");
        let fileName = fileValue[fileValue.length-1];
        $(this).parent().find(".custom-file-label").text(fileName);
    } else {
        $(this).parent().find(".custom-file-label").text("Choose file");
    }
});

function delRow(obj) {
    $(obj).closest(".custom-file").remove();
}

function delFile(obj, head) {
    let delIdRow = "<input type='hidden' name='del-file' value='" + $(obj).parent().find(".del-file-id").val() + "'/>";
    $(head).append(delIdRow);
    delRow(obj);
}

function initCheckbox(all, child) {
    $(all).change(function() {
        $(child).prop("checked", $(all).is(":checked"));
    })
    $(child).change(function() {
        $(all).prop("checked", $(child).length == getCheckedLength(child));
    });
    function getCheckedLength(child) {
        let result = 0;
        $(child).each(function() {
            if($(this).is(":checked")) result++;
        });
        return result;
    }
}

function deleteContent(target, title, id) {
    if(confirm("해당 " + title + "을(를) 삭제하시겠습니까?")) {
        $.ajax({
            type: "POST",
            url: "/admin" + target,
            data: {
                id: id
            }
        })
        .done(function(data) {
            alert("삭제되었습니다.");
            history.back();
        })
        .fail(function(xhr, status, e) {
           alert("오류가 발생했습니다.");
           console.log(e);
        });
    }
}

function deleteContents(target, param, id) {
    if(param.length < 1) {
        alert("삭제할 항목을 체크해주세요.");
    } else {
        if(confirm("선택한 항목들을 삭제하시겠습니까?")) {
            let targets = new Array();
            param.each(function() {
                targets.push($(this).parent().find("." + id).val());
            });
            $.ajax({
                type: "POST",
                url: "/admin" + target,
                data: {
                    targets: targets
                }
            })
            .done(function(data) {
                alert("선택한 항목들이 삭제되었습니다.");
                location.reload();
            })
            .fail(function(xhr, status, e) {
                alert("오류가 발생했습니다.");
                console.log(e);
            });
        }
    }
}