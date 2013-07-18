package jp.ddo.chiroru.sample;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BookshelfDaoImplTest {

    private BookshelfDao dao;

    @Before
    public void setUp()
            throws Exception {
        dao = new BookshelfDaoImpl();
    }

    @Test
    public void CRUDテスト()
            throws Exception {
        Bookshelf b = new Bookshelf();
        b.setName("name 1");
        b.setDescription("description 1");
        dao.regist(b);
        List<Bookshelf> l = dao.findAll();
        String findByIdKey = null;
        //assertThat(l.size(), is(1));
        for (Bookshelf r : l) {
            assertThat(r.getName(), is ("name 1"));
            assertThat(r.getDescription(), is ("description 1"));
            findByIdKey = r.getId();
            dao.remove(findByIdKey);
        }
        //dao.remove(findByIdKey);
    }
}
