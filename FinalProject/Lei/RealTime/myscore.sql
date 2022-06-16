create table yokey_zcst_score
(
    kch   varchar(10) not null comment '课程编号'
        primary key,
    kcmc  tinytext    not null comment '课程名称',
    bfzcj float       not null comment '成绩',
    type  tinytext    not null comment '课程类型'
);

INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2023004', 'Web前端技术', 99, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2021006', '机器学习基础', 99, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2011003', '形式语言与自动机', 98, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2023006', 'Java海量数据分布式开发', 97, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2021002', '计算机组成原理', 95, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2051001', '高级语言课程设计', 95, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2023002', ' 开源大数据核心技术', 95, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2051002', '数据结构与算法课程设计', 95, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2011002', '软件工程', 95, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2023005', 'Java EE应用开发技术', 95, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2021009', 'C语言程序设计基础', 94, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2011001', '离散数学', 94, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2011006', '云计算与大数据', 94, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2021001', '数据结构', 93, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2021008', 'Java面向对象程序设计', 93, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2021007', 'Python程序设计', 93, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2021005', '计算机网络', 92, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2021003', '操作系统', 91, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2051004', 'MySQL数据库技术', 91, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2023008', '数据仓库理论与实践', 91, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2051005', '大数据推荐系统实践', 91, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2011078', '大数据导论', 90, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2051003', 'Linux操作系统', 90, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2021004', '数据库概论', 90, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2011005', '阿里云大咖课堂', 90, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2013002', '企业大数据技术与应用', 90, '专业课');
INSERT INTO score.yokey_zcst_score (kch, kcmc, bfzcj, type) VALUES ('B2023003', 'Python数据分析技术', 90, '专业课');
