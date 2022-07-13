import boto3

def start_rds():
    client = boto3.client('rds')

    # start specific RDS instance
    response = client.start_db_instance(
        DBInstanceIdentifier='database-instance-id'
    )
    print(response)

def lambda_handler(event, context):
    start_rds()