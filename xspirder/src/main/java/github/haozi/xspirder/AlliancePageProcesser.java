package github.haozi.xspirder;

import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author wanghao
 * @Description
 * @date 2019-12-12 16:56
 */
public class AlliancePageProcesser implements PageProcessor {
    private Site site = Site.me().setDomain("www.aii-alliance.org");

    @Override
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        page.putField("title", page.getHtml().getDocument().select(".inside_content_title").text());
        page.putField("content", page.getHtml().css(".inside_content_text").smartContent().toString());
        if (StringUtils.isEmpty(page.getResultItems().get("title") )) {
            //skip this page
            page.setSkip(true);
        }
        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(http://www\\.aii\\-alliance\\.org/index\\.php\\?m=content&c=index&a=show&catid=18&id=\\d+)").all());
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args) {
        Spider.create(new AlliancePageProcesser())
                //从哪个url开始抓
                .addUrl("http://www.aii-alliance.org/index.php?m=content&c=index&a=lists&catid=18")
                .addPipeline(new ConsolePipeline())
                .addPipeline(new FilePipeline("E:\\wanghao\\git-repo\\fast_reaper\\data"))
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
    }
}
