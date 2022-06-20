import json
import urllib

import scrapy
import time
import math
import logging


class AliJobSpider(scrapy.Spider):
    name = "alibaba"  # 阿里招聘

    def __init__(self, name=None, **kwargs):
        super().__init__(name, **kwargs)
        self.pageSize = 50
        self.url_base = "https://talent.alibaba.com/position/search?_csrf=7a931187-b898-4a87-8853-cf35a78bfbe5"
        self.payload = {"channel": "group_official_site", "language": "zh", "batchId": "", "categories": "",
                        "deptCodes": [], "key": "", "pageIndex": 1, "pageSize": self.pageSize,
                        "regions": "", "subCategories": ""}
        self.cookies = {
            'cna': 'uIiyF00GBU4CAduJjegaSOB3',
            't': 'd40164c53b925a2b94cea63e5901906c',
            'ali_apache_id': '11.141.198.230.1650817713178.405486.4',
            'xman_us_f': 'x_l=1',
            'xman_t': 'yDHPqDw+64nLTgVwYDSbnQrB8KSiTL2WpNByaG/YZJDzDBJjkZvJgHe6xTa8BQrMYsiF69bXGOVuTwnutJ4mr5f2SWfcaZ8+Oh8B0MflE5w=',
            'ali_apache_track': '""',
            'xman_f': '1yswYIxyxmXs9It3EtAaqAFJ4slZoXY9Psuz+ztj1K8PgZXNg1vTGz5SNVrSp5LXcG+m8wAVI9dMmYftQvPPdV1Jmy1oSlLL9xKQLSAG35/dkEWKJgukfA==',
            'sgcookie': 'E100Z%2BoGfYDAOAKfuhZpu4hApPYIfo12AX0wf5OIyPxP0yRqE6B1wMt7%2F%2Fubi4ESWaTg9nf9HzOBTH46%2BQzUv%2BXPWWfSK829MQbOH90wNRVGPtXsnLuBxTRpw%2FqxeZVbycUf',
            'xlly_s': '1',
            'XSRF-TOKEN': '7a931187-b898-4a87-8853-cf35a78bfbe5',
            'SESSION': 'MjJBRjA1RDcwRjYwRkU1NEY2RThEMTI5MzQ0QTdCMkY=',
            'l': 'eBNRBb77LJcbhGsNBO5alurza77O3KOX1PVzaNbMiInca1xPwUfRhNChBgBkkdtjgt5Dcetzlk4Z8de9S-agJr9_PwJbsiooVpp68e1..',
            'tfstk': 'cvd5BVZICHd4OhkUaUgqUvMq1Bf5CNEf8Y_pPmEEylLSBY3RmW1m35S1WxJ3j67dl',
            'isg': 'BHZ2lH3qCFkAUfxAanue6pu2x6p4l7rRAMW3KOBWfNnBIx29SCQt4usVO_9Pi7Lp',
        }

        self.headers = {
            'authority': 'talent.alibaba.com',
            'accept': 'application/json, text/plain, */*',
            'accept-language': 'zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7,zh-TW;q=0.6',
            'cache-control': 'no-cache',
            # Already added when you pass json=
            'content-type': 'application/json',
            # Requests sorts cookies= alphabetically
            # 'cookie': 'cna=uIiyF00GBU4CAduJjegaSOB3; t=d40164c53b925a2b94cea63e5901906c; ali_apache_id=11.141.198.230.1650817713178.405486.4; xman_us_f=x_l=1; xman_t=yDHPqDw+64nLTgVwYDSbnQrB8KSiTL2WpNByaG/YZJDzDBJjkZvJgHe6xTa8BQrMYsiF69bXGOVuTwnutJ4mr5f2SWfcaZ8+Oh8B0MflE5w=; ali_apache_track=""; xman_f=1yswYIxyxmXs9It3EtAaqAFJ4slZoXY9Psuz+ztj1K8PgZXNg1vTGz5SNVrSp5LXcG+m8wAVI9dMmYftQvPPdV1Jmy1oSlLL9xKQLSAG35/dkEWKJgukfA==; sgcookie=E100Z%2BoGfYDAOAKfuhZpu4hApPYIfo12AX0wf5OIyPxP0yRqE6B1wMt7%2F%2Fubi4ESWaTg9nf9HzOBTH46%2BQzUv%2BXPWWfSK829MQbOH90wNRVGPtXsnLuBxTRpw%2FqxeZVbycUf; xlly_s=1; XSRF-TOKEN=7a931187-b898-4a87-8853-cf35a78bfbe5; SESSION=MjJBRjA1RDcwRjYwRkU1NEY2RThEMTI5MzQ0QTdCMkY=; l=eBNRBb77LJcbhGsNBO5alurza77O3KOX1PVzaNbMiInca1xPwUfRhNChBgBkkdtjgt5Dcetzlk4Z8de9S-agJr9_PwJbsiooVpp68e1..; tfstk=cvd5BVZICHd4OhkUaUgqUvMq1Bf5CNEf8Y_pPmEEylLSBY3RmW1m35S1WxJ3j67dl; isg=BHZ2lH3qCFkAUfxAanue6pu2x6p4l7rRAMW3KOBWfNnBIx29SCQt4usVO_9Pi7Lp',
            'origin': 'https://talent.alibaba.com',
            'pragma': 'no-cache',
            'referer': 'https://talent.alibaba.com/off-campus/position-list',
            'sec-ch-ua': '" Not A;Brand";v="99", "Chromium";v="102", "Google Chrome";v="102"',
            'sec-ch-ua-mobile': '?0',
            'sec-ch-ua-platform': '"macOS"',
            'sec-fetch-dest': 'empty',
            'sec-fetch-mode': 'cors',
            'sec-fetch-site': 'same-origin',
            'user-agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.61 Safari/537.36',
        }

    def start_requests(self):
        yield scrapy.Request(url=self.url_base, body=json.dumps(self.payload), cookies=self.cookies,
                             headers=self.headers, method='POST', callback=self.parse)

    def parse(self, response):
        raw_data = response.json()
        if raw_data['content']['datas']:
            yield {"source": self.name, "data": raw_data['content']['datas']}
        else:
            self.log(f"Empty posts response, received data: {raw_data}", level=logging.WARNING)

        page = raw_data['content']['currentPage']
        total_item = raw_data['content']['totalCount']
        total_page = math.ceil(total_item / self.pageSize)
        if int(page) < total_page:
            payload = self.payload.copy()
            payload['pageIndex'] = int(page) + 1
            yield scrapy.Request(url=self.url_base, body=json.dumps(payload), cookies=self.cookies,
                                 headers=self.headers, method='POST', callback=self.parse)
            self.log(f"Finish page {page} of {total_page}", level=logging.INFO)
