function handler(event) {
    var request = event.request;
    var uri = request.uri;

    // sample 1
    // CloudFront URL https://example.com/first_app1_path/function/list.html
    // before sending request to Origin, replace "/first_app1_path" to "/pc"
    request.uri = request.uri.replace(/^\/first_app1_path\//, "/pc/");
    // Origin URL: https://origin.example.com/pc/function/list.html
    console.log("new uri 1: " + request.uri);


    // sample 2
    // CloudFront URL https://example.com/any_first_path_string/function/list.html
    // before sending request to Origin, replace "/any_first_path_string/" to "/mobile"
    request.uri = request.uri.replace(/^\/[^/]*\//, "/mobile/");
    // Origin URL: https://origin.example.com/mobile/function/list.html
    console.log("new uri 2: " + request.uri);

    // return request to continue
    return request;
}