package jp.ddo.chiroru.sample;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BookshelfDaoImplTest {

    private BookshelfDao dao;

    @Before
    public void setUp()
            throws Exception {
        dao = new BookshelfDaoImpl();
    }

    @After
    public void tearDown() {
        List<Bookshelf> rl  = dao.findAll();
        for (Bookshelf b : rl) {
            dao.remove(b.get_id());
        }
        dao = null;
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
        assertThat(l.size(), is(1));
        for (Bookshelf r : l) {
            assertThat(r.getName(), is ("name 1"));
            assertThat(r.getDescription(), is ("description 1"));
            findByIdKey = r.get_id();
            assertThat(findByIdKey, is(notNullValue()));
            r.setName("updated");
            int saveCount = dao.save(r);
            assertThat(saveCount, is(1));
        }

        int removeCount = dao.remove(findByIdKey);
        assertThat(removeCount, is(1));
    }

    @Test
    public void 部分検索() {
        for (int i = 0; i< 100; i++) {
            Bookshelf b = new Bookshelf();
            b.setName("name " + i);
            b.setDescription("description " + i);
            dao.regist(b);
        }
        List<Bookshelf> l  = dao.findAll(10, 10);
        int count = 10;
        for (Bookshelf b : l) {
            assertThat(b.getName(), is ("name " + count));
            assertThat(b.getDescription(), is ("description " + count));
            ++count;
        }
        List<Bookshelf> rl  = dao.findAll();
        for (Bookshelf b : rl) {
            dao.remove(b.get_id());
        }
    }
}
