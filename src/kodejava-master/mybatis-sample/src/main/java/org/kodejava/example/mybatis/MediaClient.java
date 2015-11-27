package org.kodejava.example.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.kodejava.example.mybatis.domain.Record;
import org.kodejava.example.mybatis.persistence.RecordMapper;

import java.io.Reader;

public class MediaClient {
    public static void main(String[] args) throws Exception {
        String resource = "Configuration.xml";
        Reader reader = Resources.getResourceAsReader(resource);

        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(reader);

        SqlSession session = factory.openSession();
        try {
            RecordMapper mapper = session.getMapper(RecordMapper.class);
            Record record = mapper.getRecord(1);
            System.out.println("Record = " + record);
        } finally {
            session.close();
        }
    }
}
