function handler(event) {
    var request = event.request;
    var uri = request.uri;

    // sample 1
    // URL https://example.com/first_app1_path/function/list.html
    // before sending request to Origin, replace "/first_app1_path" to "/pc"
    request.uri = request.uri.replace(/^\/first_app1_path\//, "/pc/");
    // Origin URL: https://origin.example.com/pc/function/list.html
    console.log("new uri 1: " + request.uri);


    // sample 2
    // URL https://example.com/any_first_path_string/function/list.html
    // before sending request to Origin, replace "/any_first_path_string/" to "/mobile"
    request.uri = request.uri.replace(/^\/[^/]*\//, "/mobile/");
    // Origin URL: https://origin.example.com/mobile/function/list.html
    console.log("new uri 2: " + request.uri);


    // sample 3
    // https://example.com/first_path_string/second_path/list.html
    // before sending request to Origin, replace fixed string "/first_path_string/second_path/" to "/new_path/"
    request.uri = request.uri.replace("/first_path_string/second_path/", "/new_path/");
    // Origin URL: https://origin.example.com/new_path/list.html
    console.log("new uri 3: " + request.uri);


    // return request to continue
    return request;
}