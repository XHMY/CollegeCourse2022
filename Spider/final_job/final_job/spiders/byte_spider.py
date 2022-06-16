import json
import re
import urllib

import scrapy
import time
import math
import logging


class ByteJobSpider(scrapy.Spider):
    name = "bytedance"  # 字节跳动招聘

    def __init__(self, name=None, **kwargs):
        super().__init__(name, **kwargs)
        self.pageSize = 100
        self.url = 'https://jobs.bytedance.com/api/v1/search/job/posts?keyword=&limit={limit}&offset={' \
                   'offset}&job_category_id_list={job_cat}&location_code_list=&subject_id_list=&recruitment_id_list' \
                   '=&portal_type=2&job_function_id_list=&portal_entrance=1&_signature=.asMsgAAAAAYnppxsvhvz.2rDKAAJ89 '

        self.headers = {
            "Accept": "application/json, text/plain, */*",
            "Cache-Control": "no-cache",
            "Connection": "keep-alive",
            "Content-Type": "application/json",
            "Origin": "https://jobs.bytedance.com",
            "Portal-Channel": "office",
            "Portal-Platform": "pc",
            "Pragma": "no-cache",
            "Referer": "https://jobs.bytedance.com/experienced/position?keywords=&category=&location=&project=&type=&job_hot_flag=&current=6&limit=10&functionCategory=",
            "Sec-Fetch-Dest": "empty",
            "Sec-Fetch-Mode": "cors",
            "Sec-Fetch-Site": "same-origin",
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.61 Safari/537.36",
            "accept-language": "zh-CN",
            "env": "undefined",
            "sec-ch-ua": "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"102\", \"Google Chrome\";v=\"102\"",
            "sec-ch-ua-mobile": "?0",
            "sec-ch-ua-platform": "\"macOS\"",
            "website-path": "society",
            "x-csrf-token": "BBaAJR1Cdmi3clDCcTebI_l2tgJ992Jy-oTidYfzAHs="
        }

        self.cookies = {
            "s_v_web_id": "verify_l3vm4k1m_LiYXHIIv_idYB_4x8L_9LiV_jK1h9W1HpyGn",
            "device-id": "7104259863739680267",
            "channel": "office",
            "platform": "pc",
            "atsx-csrf-token": "BBaAJR1Cdmi3clDCcTebI_l2tgJ992Jy-oTidYfzAHs%3D",
            "SLARDAR_WEB_ID": "9c45acc9-38d7-4444-8487-246ff9378e07"
        }
        self.category_list = [6704215882479962371, 6704215864629004552, 6704215913488451847, 6709824272505768200,
                              6709824272514156812, 6704215901438216462, 6850051244971526414, 6794746007419619592,
                              6704215862603155720]
        self.body = '{{"keyword":"","limit":{limit},"offset":{offset},"job_category_id_list":["{job_cat}"],"location_code_list":[],' \
                    '"subject_id_list":[],"recruitment_id_list":[],"portal_type":2,"job_function_id_list":[],' \
                    '"portal_entrance":1}}'

    def start_requests(self):
        for c in self.category_list:
            yield scrapy.Request(
                url=self.url.format(limit=self.pageSize, offset=0, job_cat=c),
                method='POST',
                dont_filter=True,
                cookies=self.cookies,
                headers=self.headers,
                body=self.body.format(limit=self.pageSize, offset=0, job_cat=c),
            )

    def parse(self, response):
        raw_data = response.json()
        if raw_data['data']['job_post_list']:
            yield {"source": self.name, "data": raw_data['data']['job_post_list']}
        else:
            self.log(f"Empty posts response, received data: {raw_data}", level=logging.WARNING)

        offset = int(re.search(r'offset=(\d+)', response.url).group(1))
        job_cat = re.search(r'job_category_id_list=(\d+)', response.url).group(1)
        total_item = int(raw_data['data']['count'])
        if offset < total_item:
            yield scrapy.Request(url=self.url.format(limit=self.pageSize, offset=offset + self.pageSize, job_cat=job_cat),
                                 body=self.body.format(limit=self.pageSize, offset=offset + self.pageSize, job_cat=job_cat),
                                 cookies=self.cookies,
                                 headers=self.headers, method='POST', callback=self.parse)
            self.log(f"Finish page {offset} of {total_item}", level=logging.INFO)
        else:
            self.log(
                f"Finish {self.name} spider, {math.ceil(total_item / self.pageSize)} pages and {total_item} items.",
                level=logging.INFO)
