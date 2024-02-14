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
scoop install minikube helm # win
brew install minikube helm # osx

& minikube -p minikube docker-env --shell powershell | Invoke-Expression #win pwsh
eval ($minikube docker-env) # osx

minikube start
minikube addons enable ingress

cd .chart
helm install users .

#check service, forward from kuber:80 to local:8888
kubectl port-forward service/users-backend-service 8888:80

# http://localhost:8888 now points to users-backend-service
minikube stop
& minikube -p minikube docker-env --unset --shell powershell | Invoke-Expression #win pwsh disable minikube env


```
