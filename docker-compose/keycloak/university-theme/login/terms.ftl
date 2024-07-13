<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=false; section>
    <#if section = "header">
        ${msg("termsTitle")}
    <#elseif section = "form">
    <div id="kc-terms-text">
        ${kcSanitize(msg("termsText"))?no_esc}
    </div>
    <form class="form-actions" action="${url.loginAction}" method="POST">
        <button class="button" name="accept" id="kc-accept" type="submit">
            <span class="button-content">${msg("doAccept")}</span>
        </button>
        <button class="button" name="cancel" id="kc-decline" type="button">
            <span class="button-content">${msg("doDecline")}</span>
        </button>
    </form>
    <div class="clearfix"></div>
    <script type="text/javascript">
        document.getElementById('kc-decline').onclick = function() {
            window.location.href = 'http://api.adempiere.io:3333/realms/adempiere/protocol/openid-connect/auth?response_type=code&redirect_uri=http%3A%2F%2Fapi.adempiere.io%2Fvue&state=QURfQXBwUmVnaXN0cmF0aW9uX0lEPTEwMDAwMDZ8QXBwbGljYXRpbGljYXRpb25UeXBlPU9JQQ%3D%3D&client_id=adempiere-zk-cli&scope=openid+email+profile';
        };
    </script>
    </#if>
</@layout.registrationLayout>
