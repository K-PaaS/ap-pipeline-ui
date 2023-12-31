<%--
  Created by IntelliJ IDEA.
  User: REX
  Date: 7/5/2017
  Time: 4:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- footer :s -->
<div id="footer">
    <div class="footerIn">
        <div class="copy">
            Copyright © 2017 K-PaaS. All rights reserved.
        </div>
    </div>
</div>
<!--//footer :e -->
<%--<!-- Top 가기 :s -->--%>
<div class="follow" title="Scroll Back to Top">
    <a href="#" title="top"><img src="<c:url value='/resources/images/a_top.png'/>"></a>
</div>
<!--//Top 가기 :e -->


<%--POPUP CONFIRM :: BEGIN--%>
<div class="modal fade" id="modalConfirm" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close commonPopupConfirmCancelButton" data-dismiss="" aria-label="Close"><span aria-hidden="true"> &times; </span></button>
                <h1 id="commonPopupConfirmTitle" class="modal-title"> 알림 </h1>
            </div>
            <div class="modal-body">
                <p id="commonPopupConfirmMessage"> MESSAGE </p>
            </div>
            <div class="modal-footer">
                <div class="fr">
                    <button type="button" class="button btn_pop" id="commonPopupConfirmButtonText"> 저장 </button>
                    <button type="button" class="button btn_pop commonPopupConfirmCancelButton" data-dismiss=""> 취소 </button>
                </div>
            </div>
        </div>
    </div>
</div>
<%--POPUP CONFIRM :: END--%>


<%--POPUP ALERT :: BEGIN--%>
<div class="modal fade" id="modalAlert" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"> &times; </span></button>
                <h1 class="modal-title"> 알림 </h1>
            </div>
            <div class="modal-body">
                <p id="commonPopupAlertMessage"> MESSAGE </p>
            </div>
            <div class="modal-footer">
                <div class="fr">
                    <button type="button" class="button btn_pop fr" data-dismiss="modal"> 확인 </button>
                </div>
            </div>
        </div>
    </div>
</div>
<%--POPUP ALERT :: END--%>


<%--SPINNER :: BEGIN--%>
<div class="modal fade" id="modalSpinner" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-keyboard="false" data-backdrop="static">
    <div class="container">
        <div class="row" style="">
            <div class="loader"></div>
        </div>
    </div>
</div>
<%--SPINNER :: END--%>


<!--select 스크립트-->
<script>
    $.fn.selectDesign = function() {
        var t = $(this);
        var div = t.children("div");
        var strong = div.children("strong");
        var ul = t.children("ul");
        var li = ul.children("li");
        var door = false;

        div.click(function() {
            if(door) {
                ul.hide();
            }else{
                ul.show();
            }
            door = !door;
        });

        li.click(function() {
            var txt = $(this).text();
            strong.html(txt);
            div.click();
        });
    };

    $(".select1").selectDesign();
    $(".select2").selectDesign();
    $(".select3").selectDesign();

    // GrantedAuthorities 권한 모두 가져오는 전역 변수
    var grAry = [];

    var getGrantedAuthorities = function(id, category ,message){
        procCallAjax("/grantedAuthorities", null, function(data){

            if (RESULT_STATUS_FAIL === data.resultStatus) {
                procCallSpinner(SPINNER_STOP);
                return false;
            }

            if(category == "pipeline"){
                pipelineGrantedAuthorities(data, id, message);
            }else if(category == "contributor"){
                contributorGrantedAuthorities(data, id, message);
            }else if(category == "job"){
                jobGrantedAuthorities(data, id, message);
            }

            for(var i=0; i < data.length; i++){
                grAry[i] = data[i];
            }


        });
    };



    // 파이프라인 관련 권한 제어
    var pipelineGrantedAuthorities = function (data, id, message) {
        for(var i = 0; i < data.length; i++){
            var authCode = data[i].authCode;
            var authorityDescription = data[i].authority.description;

            if(authCode != null && authCode == id && message == null){
                if(authorityDescription == 'write'){
                    //$("#classification2").hide();
                }
                if(authorityDescription == 'read'){
                    $("#btnCreateJob").attr('onclick', 'procPopupAlert("권한이 없습니다.");');
                    $("#btnAddNewGroup").off('click').on('click', function() { procPopupAlert("권한이 없습니다."); });
                }
                if(authorityDescription == 'execute'){
                    $("#btnCreateJob").attr('onclick', 'procPopupAlert("권한이 없습니다.");');
                    $("#btnAddNewGroup").off('click').on('click', function() { procPopupAlert("권한이 없습니다."); });
                }
            }

            if(authCode != null && authCode == id && message == "delete"){
                if(authorityDescription == 'write'){
                    procPopupConfirm('파이프라인 삭제', '삭제 하시겠습니까?', 'deletePipeline();');
                }
                if(authorityDescription == 'read' || authorityDescription == 'execute'){
                    procPopupAlert("권한이 없습니다.");
                }
            }else if(authCode != null && authCode == id && message == "update"){
                if(authorityDescription == 'write'){
                    procPopupConfirm('파이프라인 수정', '수정 하시겠습니까?', 'updatePipeline();');
                }
                if(authorityDescription == 'read' || authorityDescription == 'execute'){
                    procPopupAlert("권한이 없습니다.");
                }
            }else if(authCode != null && authCode == id && message == "create"){
                if(authorityDescription == 'write'){
                    procMovePage("/pipeline/create");
                }
                if(authorityDescription == 'read' || authorityDescription == 'execute'){
                    procPopupAlert("권한이 없습니다.");
                }
            }
        }
    };


    // 참여자 관련 권한 제어
    var contributorGrantedAuthorities = function (data, id, message) {
        for(var i = 0; i < data.length; i++){
            var authCode = data[i].authCode;
            var authorityDescription = data[i].authority.description;

            if(authCode != null && authCode == id && message == "create"){
                if(authorityDescription == 'write'){
                    procMovePage("/pipeline/"+ document.getElementById('pipelineIdControlAuthority').value + "/contributor/create");
                }
                if(authorityDescription == 'read'){
                    procPopupAlert("권한이 없습니다.");
                }
                if(authorityDescription == 'execute'){
                    procPopupAlert("권한이 없습니다.");
                }
            }
            if(authCode != null && authCode == id && message == "delete"){
                if(authorityDescription == 'write'){
                    procPopupConfirm('참여자 삭제', '삭제 하시겠습니까?', 'deleteContributor();');
                }
                if(authorityDescription == 'read'){
                    procPopupAlert("권한이 없습니다.");
                }
                if(authorityDescription == 'execute'){
                    procPopupAlert("권한이 없습니다.");
                }
            }
            if(authCode != null && authCode == id && message == "update"){
                if(authorityDescription == 'write'){
                    procPopupConfirm('참여자 수정', '수정 하시겠습니까?', 'updateContributor();');
                }
                if(authorityDescription == 'read'){
                    procPopupAlert("권한이 없습니다.");
                }
                if(authorityDescription == 'execute'){
                    procPopupAlert("권한이 없습니다.");
                }
            }
        }
    };

    // job build, test, deploy 관련 제어
    var jobGrantedAuthorities = function(data, id, message){
        for(var i = 0; i < data.length; i++){
            var authCode = data[i].authCode;
            var authorityDescription = data[i].authority.description;

            if(authCode != null && authCode == id && message == "update"){
                if(authorityDescription == 'write'){
                    procPopupConfirm('JOB 수정', '수정 하시겠습니까?', 'updateJob();');
                }
                if(authorityDescription == 'read'){
                    procPopupAlert("권한이 없습니다.");
                }
                if(authorityDescription == 'execute'){
                    procPopupAlert("권한이 없습니다.");
                }
            }
            if(authCode != null && authCode == id && message == "trigger"){
                if(authorityDescription == 'write'){
                    procPopupConfirm('JOB 실행', '실행 하시겠습니까?', 'triggerJob();');
                }
                if(authorityDescription == 'read'){
                    procPopupAlert("권한이 없습니다.");
                }
                if(authorityDescription == 'execute'){
                    procPopupConfirm('JOB 실행', '실행 하시겠습니까?', 'triggerJob();');
                }
            }
            if(authCode != null && authCode == id && message == "stop"){
                if(authorityDescription == 'write'){
                    procPopupConfirm('JOB 정지', '정지 하시겠습니까?', 'stopJob();');
                }
                if(authorityDescription == 'read'){
                    procPopupAlert("권한이 없습니다.");
                }
                if(authorityDescription == 'execute'){
                    procPopupConfirm('JOB 정지', '정지 하시겠습니까?', 'stopJob();');
                }
            }
            if(authCode != null && authCode == id && message == "deployUpdate"){
                if(authorityDescription == 'write'){
                    procPopupConfirm('JOB 수정', '수정 하시겠습니까?', 'updateJob();');
                }
                if(authorityDescription == 'read'){
                    procPopupAlert("권한이 없습니다.");
                }
                if(authorityDescription == 'execute'){
                    procPopupAlert("권한이 없습니다.");
                }
            }
            if(authCode != null && authCode == id && message == "deployTrigger"){
                if(authorityDescription == 'write'){
                    procPopupConfirm('JOB 실행', '실행 하시겠습니까?', 'triggerJob(false);');
                }
                if(authorityDescription == 'read'){
                    procPopupAlert("권한이 없습니다.");
                }
                if(authorityDescription == 'execute'){
                    procPopupConfirm('JOB 롤백', '롤백 하시겠습니까?', 'triggerJob(true);');
                }
            }
            if(authCode != null && authCode == id && message == "deployTriggerRevertGreenDeploy"){
                if(authorityDescription == 'write'){
                    procPopupConfirm('운영 GREEN 배포 JOB 되돌리기', '운영 GREEN 배포 JOB 되돌리기를 실행 하시겠습니까?', 'triggerJob(false, \'<%= Constants.CHECK_YN_Y %>\');');
                }
                if(authorityDescription == 'read'){
                    procPopupAlert("권한이 없습니다.");
                }
                if(authorityDescription == 'execute'){
                    procPopupConfirm('운영 GREEN 배포 JOB 되돌리기', '운영 GREEN 배포 JOB 되돌리기를 실행 하시겠습니까?', 'triggerJob(false, \'<%= Constants.CHECK_YN_Y %>\');');
                }
            }
            if(authCode != null && authCode == id && message == "deployTriggerRollBack"){
                if(authorityDescription == 'write'){
                    procPopupConfirm('JOB 롤백', '롤백 하시겠습니까?', 'triggerJob(true);');
                }
                if(authorityDescription == 'read'){
                    procPopupAlert("권한이 없습니다.");
                }
                if(authorityDescription == 'execute'){
                    procPopupConfirm('JOB 롤백', '롤백 하시겠습니까?', 'triggerJob(true);');
                }
            }
            if(authCode != null && authCode == id && message == "testUpdate"){
                if(authorityDescription == 'write'){
                    procPopupConfirm('JOB 수정', '수정 하시겠습니까?', 'updateJob();');
                }
                if(authorityDescription == 'read'){
                    procPopupAlert("권한이 없습니다.");
                }
                if(authorityDescription == 'execute'){
                    procPopupAlert("권한이 없습니다.");
                }
            }
            if(authCode != null && authCode == id && message == "testTrigger"){
                if(authorityDescription == 'write'){
                    procPopupConfirm('JOB 실행', '실행 하시겠습니까?', 'triggerJob();');
                }
                if(authorityDescription == 'read'){
                    procPopupAlert("권한이 없습니다.");
                }
                if(authorityDescription == 'execute'){
                    procPopupConfirm('JOB 실행', '실행 하시겠습니까?', 'triggerJob();');
                }
            }
            if(authCode != null && authCode == id && message == "testStop"){
                if(authorityDescription == 'write'){
                    procPopupConfirm('JOB 정지', '정지 하시겠습니까?', 'stopJob();');
                }
                if(authorityDescription == 'read'){
                    procPopupAlert("권한이 없습니다.");
                }
                if(authorityDescription == 'execute'){
                    procPopupConfirm('JOB 정지', '정지 하시겠습니까?', 'stopJob();');
                }
            }
        }
    };

    // Job 실행, 정지, 추가, 복제, 삭제 버튼 권한 제어
    var jobContributorGrantedAuthorities = function (data, id, className) {
        for(var i = 0; i < data.length; i++){
            if(data[i].authCode != null && data[i].authCode == id){
                if(data[i].authority.description == 'write'){

                }
                if(data[i].authority.description == 'read'){
                    $('.' + className).attr('onclick', 'procPopupAlert("권한이 없습니다.");');
                    $("#btnCreatePipeline").off('click').on('click', function() { procPopupAlert("권한이 없습니다."); });
                }
                if(data[i].authority.description == 'execute'){
                    if($(".permission_contributor").hasClass("permission_contributorExecute") === true){
                        $(".permission_contributorExecute").removeClass("permission_contributor");
                    }
                    $('.' + className).attr('onclick', 'procPopupAlert("권한이 없습니다.");');
                    $("#btnCreatePipeline").off('click').on('click', function() { procPopupAlert("권한이 없습니다."); });
                }
            }
        }
    };

    var managerUUID;

    var getAuthorityList = function () {
        procCallAjax("/authorities", null, callbackAuthorityList);
    };

    var callbackAuthorityList = function(data){
        for(var i = 0; i < data.length; i++){
            if(data[i].displayName == "관리자"){
                managerUUID = data[i].id;
            }
        }
    };

    // ON LOAD
    $(document.body).ready(function () {
        getGrantedAuthorities();
    });

</script>
<!--//select 스크립트-->