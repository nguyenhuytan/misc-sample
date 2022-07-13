import boto3

def stop_rds():
    client = boto3.client('rds')

    # stop specific RDS instance
    response = client.stop_db_instance(
        DBInstanceIdentifier='database-instance-id',
        DBSnapshotIdentifier='stop-snapshot-id'
    )
    print(response)

def lambda_handler(event, context):
    stop_rds()