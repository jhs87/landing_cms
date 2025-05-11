$(function () {

    if($("#startDate").val() == null || $("#startDate").val() == '' || $("#startDateTime").val() == '') {
        start = getOneMinusMonth();
        end = getToday();
    } else {
        start = $("#startDate").val();
        end = $("#endDate").val();
    }

    $('.picker').daterangepicker({
        locale:{
            format : 'YYYY/MM/DD'
        },
        startDate: start,
        endDate: end
    },function(start,end,label){
        $('#startDate').val(start.format('YYYY-MM-DD'));
        $('#endDate').val(end.format('YYYY-MM-DD'));
    });

    $('.startPickerSingle').daterangepicker({
        locale:{
            format : 'YYYY/MM/DD'
        },
        singleDatePicker: true,
        timePicker: true,
        startDate: start
    },function(start,label){
        $('#startDate').val(start.format('YYYY-MM-DD'));
    });

    $('.endPickerSingle').daterangepicker({
        locale:{
            format : 'YYYY/MM/DD'
        },
        singleDatePicker: true,
        timePicker: true,
        endDate: end
    },function(end,label){
        $('#endDate').val(end.format('YYYY-MM-DD'));
    });

    /*$('.attendancePicker').daterangepicker({
        locale:{
            format : 'YYYY-MM-DD'
        },
        singleDatePicker: true,
        timePicker: false,
        maxDate: new Date(),
    },function(start,end,label){
        $('#startDate').val(start.format('YYYY-MM-DD'));
    });

    $('.timepicker').datetimepicker({
        format: 'LT'
    });*/

    $('[data-mask]').inputmask();

    $('.share-target').click(function(){
        let target = $(this).attr('data-share');
        $('.share-target.btn-primary').removeClass('btn-primary').addClass('btn-default');
        $(this).removeClass('btn-default').addClass('btn-primary');
        $('#target').val(target);
    });

    $('.share-type').click(function(){
        let type = $(this).attr('data-share-type');
        let url = $(this).attr('data-share-url');
        $('.share-type.btn-primary').removeClass('btn-primary').addClass('btn-default');
        $(this).removeClass('btn-default').addClass('btn-primary');
        $('#type').val(type);
        $('#shareLink').val(url);
    });

    $('.share').click(function(){
        let target = $('#target').val();
        let type = $('#type').val();
        let url = $('#shareLink').val();

        let data = {
            target: target,
            type: type,
            url: url
        }

        $.ajax({
            type: 'POST'
            , url: '/boards/share'
            , data:JSON.stringify(data)
            , contentType:"application/json"
            , success: function (data, result, res) {
                if (res.status === 200) {
                    alert('게시글이 공유되었습니다.');
                    $("#modal-default").modal('hide');
                    // location.reload();
                }
            }
            , error: function (xhr, status, error) {
                alert("처리중 오류가 발생했습니다." + " " + xhr.status + " ; " + error);
            }
        });
    });
});

function setCalc(years, months){
    $('#years').val(years);
    $('#months').val(months);
}

function getOneMinusMonth(){
    let date = new Date();
    date.setMonth(date.getMonth() - 1)
    let year = date.getFullYear();
    let month = ("0" + (1 + date.getMonth())).slice(-2);
    let day = ("0" + date.getDate()).slice(-2);

    return year + "-" + month + "-" + day;
}

function getToday(){
    let date = new Date();
    let year = date.getFullYear();
    let month = ("0" + (1 + date.getMonth())).slice(-2);
    let day = ("0" + date.getDate()).slice(-2);

    return year + "-" + month + "-" + day;
}

function getTodayMinusMonth(minus){
    let date = new Date().getMonth()-minus;
    let year = (date.getFullYear());
    let month = ("0" + (1 + date.getMonth())).slice(-2);
    let day = ("0" + (date.getDate() + 1)).slice(-2);

    return year + "-" + month + "-" + day;
}

function getTodayTime() {
    let date = new Date();
    let year = date.getFullYear(); // 년
    let month = ("0" + (1 + date.getMonth())).slice(-2); // 월
    let day = ("0" + date.getDate()).slice(-2); // 일
    let hours = date.getHours(); // 시
    let minutes = date.getMinutes();  // 분
    let seconds = date.getSeconds();  // 초
    return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
}

