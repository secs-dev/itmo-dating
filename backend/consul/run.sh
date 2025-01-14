#!/bin/sh

consul agent -config-file=/consul/config/consul.hcl -bootstrap-expect=1 | grep -v "This request used the token query parameter which is deprecated and will be removed"
