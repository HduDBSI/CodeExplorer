public class A{
    public JsonValue get(Url url) {
        try {
            URLConnection connection = new URL(url + "").openConnection();
            return createItemFromResponse(url, connection);
        } catch (IOException e) {
            throw ItemscriptError.internalError(this, "get.IOException", e);
        }
    }
}