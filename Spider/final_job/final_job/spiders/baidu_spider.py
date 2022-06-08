import json
import urllib

import scrapy
import time
import math
import logging


class BaiduJobSpider(scrapy.Spider):
    name = "baidu"  # 百度招聘

    def __init__(self, name=None, **kwargs):
        super().__init__(name, **kwargs)
        self.pageSize = 10
        self.url = 'https://talent.baidu.com/httservice/getPostList'

        self.headers = {
            "Accept": "application/json, text/javascript, */*; q=0.01",
            "Accept-Language": "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7,zh-TW;q=0.6",
            "Cache-Control": "no-cache",
            "Connection": "keep-alive",
            "Content-Type": "application/x-www-form-urlencoded",
            "Origin": "https://talent.baidu.com",
            "Pragma": "no-cache",
            "Referer": "https://talent.baidu.com/static/index.html",
            "Sec-Fetch-Dest": "empty",
            "Sec-Fetch-Mode": "cors",
            "Sec-Fetch-Site": "same-origin",
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.61 Safari/537.36",
            "X-Requested-With": "XMLHttpRequest",
            "sec-ch-ua": "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"102\", \"Google Chrome\";v=\"102\"",
            "sec-ch-ua-mobile": "?0",
            "sec-ch-ua-platform": "\"macOS\""
        }

        self.cookies = {
            "BIDUPSID": "0396EE4DD7CEBD6CD8CECCDE5E7753D5",
            "PSTM": "1596680768",
            "USERTOKEN": "0a4a535b113d8fc30c22590102020740be1ecdf6",
            "__yjs_duid": "1_35a3e81652afa0a8ea4f42adf5ea66d81619966974685",
            "BAIDUID": "FF25A57BC298721F8249BA54DB63491C:FG=1",
            "MCITY": "-140%3A",
            "H_WISE_SIDS": "107318_110085_127969_164869_179350_184716_187605_189034_189326_189755_190617_191068_191248_191287_192206_192391_192408_192957_193283_193559_194085_194520_195188_195342_195631_196046_196426_197241_197472_197478_197711_197783_197947_197956_198515_198648_198929_199082_199157_199177_199305_199468_199490_199576_199752_200128_200274_200452_200549_200744_200960_201055_201328_201444_201541_201555_201576_201580_201699_201733_201824_201933_201948_201978_201996_202145_202178_202392_202476_202554_202562_202564_202820_202910_202969_203078_8000058_8000101_8000130_8000140_8000143_8000149_8000159_8000162_8000167_8000178_8000185",
            "BAIDUID_BFESS": "163155D47A4C36042D504CCC4F884DC8:FG=1",
            "ZFY": "kC6Iwx7O5RO2J9BATKTfBBDlXgWZaO:BLZ15wlhiRSig:C",
            "ariaDefaultTheme": "undefined",
            "RT": "\"z=1&dm=baidu.com&si=skwx7gmot5c&ss=l3pl0f7i&sl=0&tt=0&bcn=https%3A%2F%2Ffclog.baidu.com%2Flog%2Fweirwood%3Ftype%3Dperf&ld=7rd&ul=7un5d&hd=7unap\"",
            "BAIDU_WISE_UID": "wapp_1654007892222_126",
            "Hm_lvt_50e85ccdd6c1e538eb1290bc92327926": "1654089557,1654164969",
            "Hm_lpvt_50e85ccdd6c1e538eb1290bc92327926": "1654177746"
        }

        self.body = 'postType=&workPlace=&recruitType=SOCIAL&keyWord=&pageSize={}&curPage={}&keyWord2='

    def start_requests(self):
        yield scrapy.Request(
            url=self.url,
            method='POST',
            dont_filter=True,
            cookies=self.cookies,
            headers=self.headers,
            body=self.body.format(self.pageSize, 1),
        )

    def parse(self, response):
        raw_data = response.json()
        if raw_data['data']['list']:
            yield {"source": self.name, "data": raw_data['data']['list']}
        else:
            self.log(f"Empty posts response, received data: {raw_data}", level=logging.WARNING)

        page = raw_data['data']['pageNum']
        total_page = raw_data['data']['pages']
        if int(page) < total_page:
            yield scrapy.Request(url=self.url, body=self.body.format(self.pageSize, page + 1), cookies=self.cookies,
                                 headers=self.headers, method='POST', callback=self.parse)
            self.log(f"Finish page {page} of {total_page}", level=logging.INFO)
        else:
            self.log(f"Finish {self.name} spider, {total_page} pages and {raw_data['data']['total']} items.",
                     level=logging.INFO)
