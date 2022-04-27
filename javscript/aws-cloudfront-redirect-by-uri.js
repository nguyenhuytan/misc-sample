function handler(event) {
    var request = event.request;
    var uri = request.uri;

    // Check whether the URI is endsWith /login
    if (uri.endsWith('/login')) {
        // create redirect response
        var newurl = 'https://login.sample.com/';
        var response = {
            statusCode: 301,
            statusDescription: 'Moved Permanently',
            headers:
              { "location": { "value": newurl } }
        }
        // return redirect response
        return response;
    } else {
        // return request to continue
        return request;
    }
}