create table prof_avg_score
(
    bfzcj float    not null comment '成绩',
    type  tinytext not null comment '课程类型'
);

INSERT INTO score.prof_avg_score (type, `avg(bfzcj)`) VALUES ('专业课', 93.33333333333333);
