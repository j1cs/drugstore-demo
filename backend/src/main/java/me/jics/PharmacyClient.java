package me.jics;

import io.micronaut.http.client.annotation.Client;

@Client("${services.minsal.url}")
interface PharmacyClient extends PharmacyOperations {
}
