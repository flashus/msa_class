# msa_class

Microservices architecture class repo

run app:

```bash
./gradlew build
docker compose build
docker compose up
```

run in k8s:

```bash
scoop install minikube helm

minikube start
minikube addons enable ingress

cd .chart
helm install users .

#check service, forward from kuber:80 to local:8888
kubectl port-forward service/users-backend-service 8888:80

# http://localhost now points to users-backend-service
```
