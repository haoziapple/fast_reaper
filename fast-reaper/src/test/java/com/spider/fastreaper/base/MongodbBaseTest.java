package com.spider.fastreaper.base;

/**
 * mongodb测试基类
 * 所有需要Mongo的测试，可以继承这个类，就可以获取db了
 */
public class MongodbBaseTest {
//    private static final MongodStarter starter = MongodStarter.getDefaultInstance();
//    protected MongoClient mongo;
//    protected DB db;
//    private MongodExecutable mongodExecutable;
//    private MongodProcess mongod;
//
//    @Before
//    public void setUp() throws Exception {
//        mongodExecutable = starter.prepare(new MongodConfigBuilder()
//                .version(Version.Main.PRODUCTION)
//                .net(new Net(12345, Network.localhostIsIPv6())).build());
//        mongod = mongodExecutable.start();
//
//
//        mongo = new MongoClient("localhost", 12345);
//        db = mongo.getDB("embedded-mongo");
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        mongod.stop();
//        mongodExecutable.stop();
//    }
}
