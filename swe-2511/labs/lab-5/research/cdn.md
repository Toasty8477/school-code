Security Concerns:
- If sensitive data is stored on the CDN it is more likely a data breach could occur because there are many servers instead of one.
- Because CDNs cache data they are vunerable to cache poisoning. Cache poisoning could be altering cache on the server to be malicious or pushing out relevant content to increase load on the orign server to cause a denial of service.

Integrity:
- Allows you to provide a base64 encoded hash the linked resource must match to check if it has been tampered with.
- Tries to mitigate man-in-the-middle type attacks. If anything in the file has been tampered with the hash will not match the one provided and the resource will not be loaded.

Cross Origin:
- crossorigin uses Cross Origin Resource Sharing to determine whether a sites frontend JavaScript can access resources that are not from the same origin server.
- CORS blocks cross origin requests by default so that malicious resources can't access your website.