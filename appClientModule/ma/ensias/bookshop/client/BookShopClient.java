package ma.ensias.bookshop.client;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ma.ensias.bookshop.facade.BookShopFacadeBeanRemote;
import ma.ensias.bookshop.persistance.Article;

public class BookShopClient {
	
	public BookShopClient() {
		
	}

	InitialContext getInitialContext() throws javax.naming.NamingException {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY,"org.wildfly.naming.client.WildFlyInitialContextFactory");
        p.put(Context.PROVIDER_URL,"http-remoting://localhost:8080");
		return new InitialContext(p);
	}

	public static void main(String[] args) {
		BookShopClient bookShopClientTest = new BookShopClient();
		bookShopClientTest.doTest();
	}

	void doTest() {
		try {
			InitialContext ic = getInitialContext();
			BookShopFacadeBeanRemote beanRemote=(BookShopFacadeBeanRemote) ic.lookup("ejb:bookShop/bookShopEjb/BookShopFacadeBean!ma.ensias.bookshop.facade.BookShopFacadeBeanRemote");
			System.out.println("listeArticles...");
			Collection articleList = beanRemote.getAllArticles();
			System.out.println("Printing Articles list");
			for (Article art : (List<Article>) articleList) {
				System.out.println(" -- " + art.getNumeroArticle()+ " "+art.getLibelle()+" "+art.getPrix());
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
