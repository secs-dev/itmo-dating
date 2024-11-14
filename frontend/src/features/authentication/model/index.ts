import {JwtToken} from "@/shared/api";

export interface AuthRequest {
    string: string;
    hash: string;
}

export interface AuthResponse {
    access: JwtToken;
}