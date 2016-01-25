package org.cheetyan.weibospider.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.cheetyan.weibospider.model.util.HttpClientPoolUtil;
import org.cheetyan.weibospider.plugins.Plugin;
import org.cheetyan.weibospider.spider.Timeline;
import org.cheetyan.weibospider.spider.TimelineTX;
import org.cheetyan.weibospider.taskmodel.SinaTasks;
import org.cheetyan.weibospider.taskmodel.Task;
import org.cheetyan.weibospider.taskmodel.TxTasks;
import org.cheetyan.weibospider.taskthread.SinaTaskPublicThread;
import org.cheetyan.weibospider.taskthread.TXTaskPublicThread;
import org.cheetyan.weibospider.taskthread.TXTaskUserThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration
@ComponentScan(basePackages="org.cheetyan")
@ImportResource("classpath:root-context.xml")
public class Config {
	private static Logger logger = LoggerFactory.getLogger(Config.class.getName());
	@Resource(name = "pluginList")
	private List<Plugin> pluginList;
	@Resource
	private SinaTasks sinaTasks;
	@Resource
	private TxTasks txTasks;
	@Resource
	private Timeline tm;
	@Resource
	private TimelineTX tmtx;
	
	public Config() {
	}

	private List<Runnable> getSinaTaskPublicThreads() {
		List<Runnable> runlist = new ArrayList<Runnable>();
		for (Task ts : sinaTasks.getSinaTasks2()) {
			runlist.add(new SinaTaskPublicThread(ts,pluginList,tm));
		}
		return runlist;
	}

	private List<Runnable> getTXTask1Threads() {
		List<Runnable> runlist = new LinkedList<Runnable>();
		for (Task ts : txTasks.getTxTasks1()) {
			runlist.add(new TXTaskUserThread(ts,pluginList,tmtx));
		}
		return runlist;
	}

	private List<Runnable> getTXTask2Threads() {
		List<Runnable> runlist = new LinkedList<Runnable>();
		for (Task ts : txTasks.getTxTasks2()) {
			runlist.add(new TXTaskPublicThread(ts,pluginList,tmtx));
		}
		return runlist;
	}

	public List<Runnable> getAllThreads() {
		List<Runnable> runlist = new ArrayList<Runnable>();
		runlist.addAll(this.getSinaTaskPublicThreads());
		runlist.addAll(this.getTXTask1Threads());
		runlist.addAll(this.getTXTask2Threads());
		logger.info(runlist.toString());
		return runlist;
	}

	/**
	 * note: call this method will block the current thread and wait for all threads to stop
	 * 
	 * @throws InterruptedException
	 */
	public void getAllThreadsRun() {
		List<Thread> ts = new ArrayList<Thread>();
		for (Runnable t : getAllThreads()) {
			Thread t1 = new Thread(t);
			ts.add(t1);
			t1.start();
		}
		for (Thread t : ts) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (Plugin s : pluginList) {
			s.close();
		}
	}

	public SinaTasks getSinaTasks() {
		return sinaTasks;
	}

	public void setSinaTasks(SinaTasks sinaTasks) {
		this.sinaTasks = sinaTasks;
	}

	public TxTasks getTxTasks() {
		return txTasks;
	}

	public void setTxTasks(TxTasks txTasks) {
		this.txTasks = txTasks;
	}

	public static void main(String... strings) throws IOException {
		AnnotationConfigApplicationContext ctx =new AnnotationConfigApplicationContext();
		ctx.register(Config.class);
		ctx.refresh();
		Config config = ctx.getBean(Config.class);
		config.getAllThreadsRun();
		//ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "beans.xml" });
		//context.registerShutdownHook();
		//TaskHandle th = context.getBean(TaskHandle.class);
		//th.getAllThreadsRun();
		HttpClientPoolUtil.shutDown();
		ctx.close();
	}
}
