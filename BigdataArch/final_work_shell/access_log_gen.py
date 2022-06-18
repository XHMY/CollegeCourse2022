import requests
import pyuser_agent

obj = pyuser_agent.UA()

headers = {
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
    'Accept-Language': 'zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7,zh-TW;q=0.6',
    'Cache-Control': 'no-cache',
    'Connection': 'keep-alive',
    # Requests sorts cookies= alphabetically
    # 'Cookie': 'stg_returning_visitor=Thu%2C%2023%20Dec%202021%2014:52:39%20GMT; _pk_id.5cd2af31-892f-43fc-89ba-dbb835c4ec39.dc78=99ac5c6b90be7a56.1640270800.1.1640275522.1640270800.; stg_last_interaction=Thu%2C%2023%20Dec%202021%2016:27:57%20GMT; session=.eJwlz0FuAzEIheG7eD0LsGGAXGZkY1CjRI00k6yq3r2WeoBP_3s_5cgzrq9ye5-f2Mpxn-VWlFFTLMDJtA5iTYTZm0KikWNl6NgCh1UUVQ4zy75bcAwFGCLSKUVHJAFT5d2h5mggRO7G1omAGNuebK1Zd1UVjAncnVjKVvw683i_HvG99hCvgLuY54RKxmM6SsvJ6LPlPiGVhW2558v7M5ZZcCufK87_S1h-_wAYFUFR.YqvvFQ.bBW3NkJaU25OWC_zmShJT3G84h0',
    'Pragma': 'no-cache',
    'Sec-Fetch-Dest': 'document',
    'Sec-Fetch-Mode': 'navigate',
    'Sec-Fetch-Site': 'none',
    'Sec-Fetch-User': '?1',
    'Upgrade-Insecure-Requests': '1',
    'User-Agent': obj.random,
    'sec-ch-ua': '" Not A;Brand";v="99", "Chromium";v="102", "Google Chrome";v="102"',
    'sec-ch-ua-mobile': '?0',
    'sec-ch-ua-platform': '"macOS"',
}

for i in range(10):
    headers['User-Agent'] = obj.random
    response = requests.get('http://127.0.0.1/hi', headers=headers, data={"id":1,"data":"python say hi."})