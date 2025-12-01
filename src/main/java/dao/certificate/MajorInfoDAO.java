package dao.certificate;

import dto.certificate.MajorInfoDTO;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.net.URL;
import java.net.URI;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayInputStream;
public class MajorInfoDAO {

    private static final String API_KEY = "1c1092e86b977fcc94d62ae9688c9896";
    private static final String BASE_URL = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=" + API_KEY
                                          + "&svcType=api&svcCode=MAJOR_VIEW&contentType=xml&gubun=univ_list&majorSeq=";
                                          
    public MajorInfoDTO getMajorInfoFromAPI(String majorSeq) throws Exception {
        
        String apiUrl = BASE_URL + majorSeq;
        
        URI uri = new URI(apiUrl);
        URL url = uri.toURL();
        
        try (InputStream xmlStream = url.openStream()) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(parsedStream);
            XPath xpath = XPathFactory.newInstance().newXPath();

            MajorInfoDTO dto = new MajorInfoDTO();

            // XPath로 각 노드 추출 및 DTO에 설정
            dto.setMajorName(extractNodeValue(doc, xpath, "/dataSearch/content/major"));
            dto.setRelatedJobs(extractNodeValue(doc, xpath, "/dataSearch/content/job"));
            dto.setQualifications(extractNodeValue(doc, xpath, "/dataSearch/content/qualifications"));
            return dto;
            
        } catch (Exception e) {
            System.err.println("!!! API 호출 또는 파싱 중 치명적인 오류 발생 !!!");
            System.err.println("에러 메시지: " + e.getMessage());
            throw e;
        }
    }
    
    // XPath로 특정 노드의 값을 추출하는 헬퍼 메서드
    private String extractNodeValue(Document doc, XPath xpath, String expression) throws Exception {
        Node node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
        return (node != null) ? node.getTextContent().trim() : "정보 없음";
    }
}
