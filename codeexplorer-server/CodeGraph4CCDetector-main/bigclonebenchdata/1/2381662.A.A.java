public class A{
    @Test(expected = GadgetException.class)
    public void malformedGadgetSpecThrows() throws Exception {
        HttpRequest request = createIgnoreCacheRequest();
        expect(pipeline.execute(request)).andReturn(new HttpResponse("malformed junk"));
        replay(pipeline);
        specFactory.getGadgetSpec(createContext(SPEC_URL, true));
    }
}