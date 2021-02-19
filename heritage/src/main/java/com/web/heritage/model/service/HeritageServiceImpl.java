package com.web.heritage.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.web.heritage.model.Heritage;
import com.web.heritage.model.HeritageImage;
import com.web.heritage.model.Marker;
import com.web.heritage.model.mapper.HeritageMapper;

@Service
public class HeritageServiceImpl implements HeritageService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Marker> listHeritage(String word) throws SQLException {
		return sqlSession.getMapper(HeritageMapper.class).listHeritage(word);
	}

	@Override
	public Heritage getHeritage(String kdcd, String ctcd, String asno) throws SQLException {
		return sqlSession.getMapper(HeritageMapper.class).getHeritage(kdcd, ctcd, asno);
	}

	@Override
	public List<HeritageImage> getImages(String kdcd, String ctcd, String asno) throws Exception {
		List<HeritageImage> images = new ArrayList<HeritageImage>();
		String url = "http://www.cha.go.kr/cha/SearchImageOpenapi.do?ccbaKdcd=" + kdcd
			+ "&ccbaAsno=" + asno
			+ "&ccbaCtcd=" + ctcd;

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(url);

			doc.getDocumentElement().normalize(); //문서 정규화
			NodeList listImage = doc.getElementsByTagName("imageUrl"); //System.out.println("파싱할 리스트 수: " + listImage.getLength());
			NodeList listDesc = doc.getElementsByTagName("ccimDesc"); //System.out.println("파싱할 리스트 수: " + listDesc.getLength());

			for (int i = 0; i < listImage.getLength(); i++) {
				Node nodeImage = listImage.item(i);
				Node nodeDesc = listDesc.item(i);

				if (nodeImage.getNodeType() == Node.ELEMENT_NODE) {
					HeritageImage image = new HeritageImage();
					Element elemImage = (Element)nodeImage;
					image.setUrl(elemImage.getTextContent());
					Element elemDesc = (Element)nodeDesc;
					image.setDesc(elemDesc.getTextContent());
					images.add(image);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return images;
	}

}
