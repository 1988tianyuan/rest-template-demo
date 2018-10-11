import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.liugeng.pojo.CommonResponse;
import com.liugeng.pojo.Person;

public class RestTemplateTest {

	private RestTemplate restTemplate;
	private String urlPrefix = "http://127.0.0.1:8081";
	private final Logger logger = LoggerFactory.getLogger(RestTemplateTest.class);

	{
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(20000);
		factory.setReadTimeout(5000);

		restTemplate = new RestTemplate(factory);
	}


	@Test
	public void getPersonListTest(){
		String url = urlPrefix + "/persons";
		CommonResponse response = restTemplate.getForObject(url, CommonResponse.class);
		logger.debug("\n\n persons: 请求成功, " + response);
	}

	@Test
	public void getOnePersonTest(){
		String url = urlPrefix + "/persons/{id}";
		Map<String, Object> urlVariables = new HashMap<>();
		urlVariables.put("id", "0");
		CommonResponse response = restTemplate.getForObject(url, CommonResponse.class, urlVariables);
		logger.debug("\n\n one person: 请求成功, " + response);

	}

	@Test
	public void getPersonByNameTest(){
		String url = urlPrefix + "/persons/getByName";
		Map<String, Object> params = new HashMap<>();
		params.put("name", "张三");

		//获取到的是ResponseEntity
		ResponseEntity<CommonResponse> responseEntity = restTemplate.getForEntity(url, CommonResponse.class, params);
		//从ResponseEntity中获取各种信息
		CommonResponse responseBody = responseEntity.getBody();
		HttpStatus status = responseEntity.getStatusCode();
		HttpHeaders headers = responseEntity.getHeaders();

		logger.debug("\n\n one person by name: 请求成功, " + responseBody + ", httpStatus: "+ status.toString() +
			", contentType: " + headers.getContentType());
	}

	@Test
	public void getListErrorTest(){
		String url = urlPrefix + "/person";
		CommonResponse response = null;
		try {
			response = restTemplate.getForObject(url, CommonResponse.class);
		} catch (RestClientException e) {
			logger.debug("\n\n persons: 请求失败, 失败代码：" + e.getMessage());
		}
	}


	@Test
	public void putOnePersonTest(){
		String url = urlPrefix + "/persons";
		try {
			restTemplate.put(url, new Person("张十一", 180.0));
			logger.debug("\n\n put one person: 请求成功!");
		} catch (RestClientException e) {
			logger.debug("\n\n put one person: 请求失败, 失败代码：" + e.getMessage());
		}
	}

	@Test
	public void deletePersonTest(){
		String url = urlPrefix + "/persons/" + String.valueOf(5);
		try {
			restTemplate.delete(url);
			logger.debug("\n\n delete one person: 请求成功!");
		} catch (RestClientException e) {
			logger.debug("\n\n delete one person: 请求失败, 失败代码：" + e.getMessage());
		}
	}

	@Test
	public void postListTest(){
		String url = urlPrefix + "/persons";
		List<Person> personList = new LinkedList<>();
		personList.add(new Person("张三", 180.0));
		personList.add(new Person("张四", 180.0));
		personList.add(new Person("张五", 180.0));
		personList.add(new Person("张六", 180.0));
		personList.add(new Person("张七", 180.0));
		personList.add(new Person("张八", 180.0));
		personList.add(new Person("张九", 180.0));
		personList.add(new Person("张十", 180.0));

		CommonResponse response = restTemplate.postForObject(url, personList, CommonResponse.class);
		logger.debug("\n\n addPerson: 请求成功, " + response);
	}

	@Test
	public void postListTest2(){
		String url = urlPrefix + "/persons";
		List<Person> personList = new LinkedList<>();
		personList.add(new Person("张十一", 180.0));
		personList.add(new Person("张十二", 180.0));

		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.set("headerName", "headerValue");
		HttpEntity<List<Person>> request = new HttpEntity<>(personList, headers);

		ResponseEntity<CommonResponse> response = restTemplate.postForEntity(url, request, CommonResponse.class);
		logger.debug("\n\n addPerson: 请求成功, " + response);
	}

	@Test
	public void downloadTest(){

	}
}






















