public class A{
    protected void givenTestRepository(String repositoryId) throws Exception {
        HttpResponse response = executeDeleteWithResponse("/repositories/" + repositoryId);
        consume(response.getEntity());
        response = executePost("/repositories", createRepositoryXml(repositoryId));
        assertEquals(content(response), SC_CREATED, statusCode(response));
    }
}