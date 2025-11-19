package test;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ApiTest {
    public static void main(String[] args) {
        String key = "c47839deeca0ab31fa74572234394d55bf24f57c305df9a0891246cf889c0537";
        String url = "http://openapi.q-net.or.kr/api/service/rest/InquiryListNationalQualifcationSVC/getList"
                + "?serviceKey=" + key;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response code: " +  response.statusCode());

            String responseBody = response.body();

            parseAndFilterXml(responseBody);


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private static void parseAndFilterXml(String xmlData) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        InputSource is = new InputSource(new StringReader(xmlData));
        Document doc = builder.parse(is);

        NodeList itemList = doc.getElementsByTagName("item");

        System.out.println("계열코드 01 자격증 목록");

        for (int i = 0; i < itemList.getLength(); i++) {

            // 5. i번째 <item> 노드를 가져옵니다.
            Node itemNode = itemList.item(i);

            // 6. 이 노드가 실제 Element 태그인지 확인합니다. (텍스트 노드 등이 아님을 보장)
            if (itemNode.getNodeType() == Node.ELEMENT_NODE) {

                // 7. Element로 형변환하여 태그 안의 다른 태그에 접근할 수 있도록 합니다.
                Element itemElement = (Element) itemNode;

                // 8. (필터링 조건) <item> 안의 <seriescd> 태그의 텍스트 값을 가져옵니다.
                String seriesCd = itemElement.getElementsByTagName("seriescd").item(0).getTextContent();

                // 9. (핵심!) 가져온 계열코드(seriesCd)가 "01"과 일치하는지 비교합니다.
                if ("04".equals(seriesCd)) {

                    // 10. ("01"이 맞을 경우에만 실행) <jmfldnm> (종목명) 태그의 값을 가져옵니다.
                    String jmFldNm = itemElement.getElementsByTagName("jmfldnm").item(0).getTextContent();

                    // 11. ("01"이 맞을 경우에만 실행) <seriesnm> (계열명) 태그의 값을 가져옵니다.
                    String seriesNm = itemElement.getElementsByTagName("seriesnm").item(0).getTextContent();

                    // 12. 필터링된 결과만 화면에 출력합니다.
                    System.out.println("자격증명: " + jmFldNm + " (계열: " + seriesNm + ")");
                }
            }
        }
    }
}
