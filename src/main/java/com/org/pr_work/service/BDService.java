package com.org.pr_work.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 百度相关服务
 * 获取当前店铺文档数
 * 获取昨日下载量 产生的收益
 * 删除审核失败的文档
 *
 * @author hsen
 */
@Service
@Slf4j
public class BDService {


    /**
     * 审核失败
     */
    private static final String FAIL_STATUS = "4";

    /**
     * 未上架
     */
    private static final String NO_SHELF_STATUS = "5";


    /**
     * 查询列表接口
     *
     * @param pageNum 页码
     * @return 查询列表接口响应
     */
    private HttpResponse<String> queryList(int pageNum) {
        // 付费 文档列表
//        String url = "https://cuttlefish.baidu.com/nshop/doc/getlist?sub_tab=2&pn= " + pageNum + "&rn=10&query=&doc_id_str=&time_range=&buyout_show_type=1&needDayUploadUserCount=1";
        // vip 文档列表
        String url= "https://cuttlefish.baidu.com/nshop/doc/getlist?sub_tab=1&pn=" + pageNum + "&rn=10&query=&doc_id_str=&time_range=&buyout_show_type=1&needDayUploadUserCount=1";
        HttpResponse<String> response = Unirest.get(url).header("Accept", " application/json, text/plain, */*").header("Accept-Language", " zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6").header("Cache-Control", " no-cache, no-store").header("Connection", " keep-alive").header("Host", " cuttlefish.baidu.com").header("Pragma", " no-cache").header("Referer", " https://cuttlefish.baidu.com/shopmis?_wkts_=1725442131889").header("Sec-Fetch-Dest", " empty").header("Sec-Fetch-Mode", " cors").header("Sec-Fetch-Site", " same-origin").header("User-Agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36 Edg/128.0.0.0").header("sec-ch-ua", " \"Chromium\";v=\"128\", \"Not;A=Brand\";v=\"24\", \"Microsoft Edge\";v=\"128\"").header("sec-ch-ua-mobile", " ?0").header("sec-ch-ua-platform", " \"Windows\"").header("Cookie", "BAIDUID=AFA5469909B591180C6EBE5A70DDB649:FG=1; BAIDUID_BFESS=AFA5469909B591180C6EBE5A70DDB649:FG=1; __bid_n=1930e0cfa4392166e06596; BDUSS=WJyVFZBUDhjZjd5V1NVS3ZrTVZaRUY3VmVnRFltK2k1WE9qbjYwbUxyS2pOWFZuQUFBQUFBPT0AAAAAAAAAAInMuSDPvLkWzrHXsMTjtcTDwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKOoTWfKmdBMZ; BDUSS_BFESS=WJyVFZBUDhjZjd5V1NVS3ZrTVZaRUY3VmVnRFltK2k1WE9qbjYwbUxyS2pOWFZuQUFBQUFBPT0AAAAAAAAAAInMuSDPvLkWzrHXsMTjtcTDwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKOoTWfKmdBMZ; passRefreshTk=CNNFqDKyhW8jeFy69Fad8dd6kqIhtDWAkxS9N2uTgEmjDsQOeFEAIvq4%2BTS9cLevFB70L7AA%2Bedl7xjCQaYgJWWrfQTtH85JaoaDfnDEHdYgNBD3A9eNLVRYazN%2F41NUtruYZlXBbzHJdbgmMzHmrrvceNx0SR1djzSbUiyyeFdXfdotokYcFfF7BFcgG36q; ab_sr=1.0.1_NGNkMjYyNzk5NWVkMzBlMDcyOWEwZjViOWU0YmYyNDBmZDk3Y2I4OTVkMjk5YzJjNmU4ZjVmMGYzMjkwNzRjODZmZDhhODI5ZDcyZTVkNjgyY2M4ZDE5NTNhYjViMDRiNTkxZjFjZWJkODU2YTM4MjdhMzUwZDYyYmI4MmZmNjQ5NTMxNGE2YzBkZjE4NjdmY2U3NmM1YmU3OTBhOTZmZWY3M2ExYjM2YjQyNDRmYmE5OTkxMjk3YWQzYWJlZjc3").asString();
        return response;
    }

    /**
     * 删除文档接口
     *
     * @param token  token
     * @param doc_id 文档id
     * @throws InterruptedException 异常
     */
    private void deleteDoc(String token, String doc_id) throws InterruptedException {
        HttpResponse<String> response1 = Unirest.get("https://cuttlefish.baidu.com/user/submit/newdocdelete?token=hs19960520" + "&new_token=" + token + "&fold_id_str=0&doc_id_str=" + doc_id + "&skip_fold_validate=1").header("Accept", " */*").header("Accept-Language", " zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6").header("Connection", " keep-alive").header("Host", " cuttlefish.baidu.com").header("Referer", " https://cuttlefish.baidu.com/shopmis?_wkts_=1725442131889").header("Sec-Fetch-Dest", " empty").header("Sec-Fetch-Mode", " cors").header("Sec-Fetch-Site", " same-origin").header("User-Agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36 Edg/128.0.0.0").header("X-Requested-With", " XMLHttpRequest").header("sec-ch-ua", " \"Chromium\";v=\"128\", \"Not;A=Brand\";v=\"24\", \"Microsoft Edge\";v=\"128\"").header("sec-ch-ua-mobile", " ?0").header("sec-ch-ua-platform", " \"Windows\"").header("Cookie", " PSTM=1702631536; BIDUPSID=DBB78032C6DEC68500FF69CC48D77A56; BAIDUID=C22CB397095E3546FB89D77051B240F2:FG; b-user-id=f37b37b2-6767-2cf7-5f2b-17cb4c6fe9b5; __bid_n=18cd3a2a9dc80484b444bc; MCITY=-234%3A; ZFY=nlq8h5fw6XBiy7S6bUXa4FOcyjLn8cEVqlfwLykOpz0:C; BAIDUID_BFESS=C22CB397095E3546FB89D77051B240F2:FG; BDUSS=TQyMy1OdTdkaTZTeEZMczJ2RkRCZXhJS1lDcWw0NlhYNlBJQU4xN3Fsa0NkR0ptRVFBQUFBJCQAAAAAAAAAAAEAAADPvLkWzrHXsMTjtcTDwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAALnOmYC5zpmZ; BDUSS_BFESS=TQyMy1OdTdkaTZTeEZMczJ2RkRCZXhJS1lDcWw0NlhYNlBJQU4xN3Fsa0NkR0ptRVFBQUFBJCQAAAAAAAAAAAEAAADPvLkWzrHXsMTjtcTDwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAALnOmYC5zpmZ; H_WISE_SIDS=60271_60339_60375; H_WISE_SIDS_BFESS=60271_60339_60375; RT=\"z; H_PS_PSSID=60448_60360_60630_60665_60678_60682_60721; BA_HECTOR=a52k8l8l202lagag2k0h2121bkiv461jdg64l1v; ab_sr=1.0.1_YjM0Y2Q4YjA1ODMzNTZjM2Y5NzAyOWM2YTMyMTEyODE1MDk3MWQzN2JlZWUxYWRlZGYyOWUwN2NkY2VlMTVlNGZmM2VmMDcwZTI5Y2ZmNzM0MGM4MTY3OTRlOTNjMGM3YWUwMzg3ZTUwZmI0OTc3OTJlZTQ1YmQ4ZTY0ZGY4NDU0YzU4MjYzMTlhODkwYjdjYTliZWZjYzA2NmIxYmNjMTM1YTExYWQ1MjQ2YmY4YTk3OTlhN2Y0ZWEzOTA1YzRiMjZhYTRhYzlhMGQ4ZmUwMTFhMTZkZGRmYWM3MDNmYjg").asString();
        log.info("deleteDoc:{}{}", response1.getStatus(), response1.getBody());
        Thread.sleep(5000);
    }


    /**
     * 获取当前店铺所有文件数
     */
    public String getFilesNumber() {

        String urlStr = "https://cuttlefish.baidu.com/search/interface/shopsearch?shop_id=4b3336d4b14e852458fb57e4&resource_type=1&pn=0&rn=8&sort_type=5";
        HttpResponse<String> response = Unirest.get(urlStr).header("Accept", " */*").header("Accept-Language", " zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6").header("Connection", " keep-alive").header("Host", " cuttlefish.baidu.com").header("Cookie", "BAIDUID=627A741B110A859AD705BAD1B802DA0C:FG=1").asString();
        JSONObject jsonObject = JSONObject.parseObject(response.getBody()).getJSONObject("data");
        return jsonObject.getJSONArray("class_result").get(0).toString();
    }

    /**
     * 开始删除不合规的文档
     *
     * @param pageNum 页码
     * @return 响应
     */
    public void startDelDoc(int pageNum) throws InterruptedException {
        // 循环获取 100 页数据
        for (int i = pageNum; i >= 0; i--) {

            HttpResponse<String> response = queryList(i);

            int status = response.getStatus();
            log.info("query_list: i={};status={};", i, status);

            JSONObject jsonObject = JSONObject.parseObject(response.getBody()).getJSONObject("data");
            String token = String.valueOf(jsonObject.get("token"));
            JSONArray doc_list = jsonObject.getJSONArray("doc_list");
            if (doc_list == null) {
                log.info("->>>>>>>>>{}", jsonObject.toJSONString());
                continue;
            }
            for (Object o : doc_list) {
                JSONObject doc = (JSONObject) o;
                String title = (String) doc.get("title");
                String doc_id = (String) doc.get("doc_id");
                String doc_status = doc.get("doc_status") + "";
                // 状态 为失败 或者是未上架 进行删除处理.
                if (FAIL_STATUS.equals(doc_status) || NO_SHELF_STATUS.equals(doc_status)) {
                    Thread.sleep(1000);
                    log.info("{}    {}----------------------------- {}", i, title, doc_id);
                    // 删除文章
                    deleteDoc(token, doc_id);

                }
            }
            Thread.sleep(10000);
        }


    }

    /**
     * 开始删除文档
     *
     * @throws InterruptedException 异常
     */
    public void startDelDoc() throws InterruptedException {
        startDelDoc(10);
    }
}
