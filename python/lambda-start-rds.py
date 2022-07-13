import boto3

def start_rds():
    client = boto3.client('rds')

    # start specific RDS instance
    response = client.start_db_instance(
        DBInstanceIdentifier='database-instance-id'
    )
    
    # start specific RDS cluster
    response = client.start_db_cluster(
        DBClusterIdentifier='database-cluster-id'
    )
    
    print(response)

def lambda_handler(event, context):
    start_rds()