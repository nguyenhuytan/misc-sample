import boto3

def shutdown_rds():
    client = boto3.client('rds')
    response = client.describe_db_instances()
    v_readReplica=[]
    for i in response['DBInstances']:
        readReplica=i['ReadReplicaDBInstanceIdentifiers']
        v_readReplica.extend(readReplica)
    
    #The if condition below filters aurora clusters from single instance databases as boto3 commands defer to stop the aurora clusters.
    #The if condition below filters Read replicas.
    #check if the RDS instance is part of the Auto-Shutdown group.
    #If the tags match, then stop the instances by validating the current status.

    for i in response['DBInstances']:
        if i['Engine'] not in ['aurora-mysql','aurora-postgresql']:
            if i['DBInstanceIdentifier'] not in v_readReplica and len(i['ReadReplicaDBInstanceIdentifiers']) == 0:
                if i['DBInstanceStatus'] == 'available':
                    client.stop_db_instance(DBInstanceIdentifier = i['DBInstanceIdentifier'])
                    print('stopping DB instance {0}'.format(i['DBInstanceIdentifier']))
                elif i['DBInstanceStatus'] == 'stopped':
                    print('DB Instance {0} is already stopped'.format(i['DBInstanceIdentifier']))
                elif i['DBInstanceStatus']=='starting':
                    print('DB Instance {0} is in starting state. Please stop the cluster after starting is complete'.format(i['DBInstanceIdentifier']))
                elif i['DBInstanceStatus']=='stopping':
                    print('DB Instance {0} is already in stopping state.'.format(i['DBInstanceIdentifier']))
            elif i['DBInstanceIdentifier'] in v_readReplica:
                print('DB Instance {0} is a Read Replica. Cannot shutdown a Read Replica instance'.format(i['DBInstanceIdentifier']))
            else:
                print('DB Instance {0} has a read replica. Cannot shutdown a database with Read Replica'.format(i['DBInstanceIdentifier']))

    response=client.describe_db_clusters()
    for i in response['DBClusters']:
        if i['Status'] == 'available':
            client.stop_db_cluster(DBClusterIdentifier=i['DBClusterIdentifier'])
            print('stopping DB cluster {0}'.format(i['DBClusterIdentifier']))
        elif i['Status'] == 'stopped':
            print('DB Cluster {0} is already stopped'.format(i['DBClusterIdentifier']))
        elif i['Status']=='starting':
            print('DB Cluster {0} is in starting state. Please stop the cluster after starting is complete'.format(i['DBClusterIdentifier']))
        elif i['Status']=='stopping':
            print('DB Cluster {0} is already in stopping state.'.format(i['DBClusterIdentifier']))

def lambda_handler(event, context):
    shutdown_rds()