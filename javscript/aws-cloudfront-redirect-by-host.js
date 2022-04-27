function handler(event) {
    var request = event.request;
    var host = request.headers.host.value;

    // compare host
    if (host == 'sample.com') {
        // create redirect response
        var newurl = 'https://www.sample.com/';
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