{{- define "users.deployment" -}}
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ .appName }}-deployment
  namespace: {{ .namespace }}
  labels:
    app: {{ .appName }}
spec:
  replicas: {{ .replicasCount }}
  selector:
    matchLabels:
      app: {{ .appName }}
  template:
    metadata:
      labels:
        app: {{ .appName }}
    spec:
      containers:
        - name: {{ .appName }}
          image: {{ .container.image }}:{{ .container.version }}
          resources:
            requests:
              memory: "256Mi"
              cpu: "100m"
            limits:
              memory: "512Mi"
              cpu: "200m"
          ports:
            - containerPort: {{ .container.port }}
{{- if .container.env }}
          env:
{{ toYaml .container.env | indent 12 }}
{{ end}}
{{- end}}


{{- define "users.service" -}}
{{ if .service }}
apiVersion: v1
kind: Service
metadata:
  name: {{ .appName }}-service
  namespace: {{ .namespace }}
spec:
  {{ if .service.type }}type: {{ .service.type }} {{ end}}
  selector:
    app: {{ .appName }}
  ports:
  - protocol: TCP
    port: {{ .service.port }}
    targetPort: {{ .service.targetPort }}
    {{ if .service.nodePort }}nodePort: {{ .service.nodePort }} {{ end}}
{{ end}}
{{- end}}