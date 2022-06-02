import scrapy
import time
import math
import logging
import re


class TencentJobSpider(scrapy.Spider):
    name = "tencent"  # 腾讯招聘

    def __init__(self, name=None, **kwargs):
        super().__init__(name, **kwargs)
        self.pageSize = 50
        self.url_base = "https://careers.tencent.com/tencentcareer/api/post/Query?timestamp={" \
                        "ts}&countryId=&cityId=&bgIds=&productId=&categoryId=&parentCategoryId=&attrId=&keyword" \
                        "=&pageIndex={page}&pageSize={ps}&language=zh-cn&area=cn"

    def start_requests(self):
        ts = str(int(time.time() * 1000))
        yield scrapy.Request(url=self.url_base.format(ts=ts, page=1, ps=self.pageSize), callback=self.parse)

    def parse(self, response):
        raw_data = response.json()
        if raw_data['Data']['Posts']:
            yield {"source": self.name, "data": raw_data['Data']['Posts']}
        else:
            self.log("Empty posts response, may have reached the tail...", level=logging.WARNING)

        page = re.search(r'pageIndex=(\d+)', response.url).group(1)
        total_item = int(raw_data['Data']['Count'])
        total_page = math.ceil(total_item / self.pageSize)
        if int(page) < total_page:
            ts = str(int(time.time() * 1000))
            yield scrapy.Request(url=self.url_base.format(ts=ts, page=int(page) + 1, ps=self.pageSize), callback=self.parse)
            self.log(f"Finish page {page} of {total_page}", level=logging.INFO)
