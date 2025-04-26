<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=false; section>
    <#if section == "header">
        <div class="header-content">
            <div class="title-with-logo">
                <svg aria-hidden="true" height="24" width="24" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path d="M0 0h24v24H0z" fill="none"></path>
                    <path d="M14 9V4H5v16h6.056c.328.417.724.785 1.18 1.085l1.39.915H3.993A.993.993 0 0 1 3 21.008V2.992C3 2.455 3.449 2 4.002 2h10.995L21 8v1h-7zm-2 2h9v5.949c0 .99-.501 1.916-1.336 2.465L16.5 21.498l-3.164-2.084A2.953 2.953 0 0 1 12 16.95V11zm2 5.949c0 .316.162.614.436.795l2.064 1.36 2.064-1.36a.954.954 0 0 0 .436-.795V13h-5v3.949z" fill="currentColor"></path>
                </svg>
                <h2>${msg("termsTitle")}</h2>
            </div>
        </div>
    <#elseif section == "form">
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
