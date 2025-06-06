FROM ubuntu:latest
LABEL authors="manish.ranjan"

ENTRYPOINT ["top", "-b"]