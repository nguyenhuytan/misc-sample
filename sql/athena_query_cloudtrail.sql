-- Document
-- 
-- SELECT - Amazon Athena
-- https://docs.aws.amazon.com/ja_jp/athena/latest/ug/select.html
-- 
-- 6.12. JSON Functions and Operators — Presto 0.217 Documentation
-- https://prestodb.io/docs/0.217/functions/json.html

-- Query CloudTrail log by Athena

-- Sample 1: Search children node value in responseElements json string by using json_extract_scalar, json_parse

select * from "TABLE_NAME" 
where "eventname" = 'EVENT_NAME' and eventtime < 'yyyy-mm-dd'
and json_extract_scalar(json_parse("responseElements"),'$.CHILDEN_NODE' ) = 'CHILDEN_NODE_VALUE'
limit 1

-- Sample 2: Search json string by using sql LIKE

select * from "TABLE_NAME"
where "eventname" = 'EVENT_NAME'
and "responseElements" like '%CHILDEN_NODE_VALUE%'
limit 1

-- replace with your values:
-- 1. TABLE_NAME
-- 2. EVENT_NAME
-- 3. yyyy-mm-dd
-- 4. CHILDEN_NODE
-- 5. CHILDEN_NODE_VALUE