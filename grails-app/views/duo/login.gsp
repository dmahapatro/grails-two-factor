<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Duo</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
    <asset:javascript src="duo/Duo-Web-v2.min.js" />
</head>
<body>
    <iframe
        id="duo_iframe"
        data-host="${duoSigResponse.hostname}"
        data-sig-request="${duoSigResponse.sigRequest}"
        data-post-action="${duoSigResponse.postCallbackUrl}"
        width="100%"
        height="420"
        frameborder="0">

    </iframe>
</body>
</html>
