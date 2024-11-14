import {JwtToken} from "@/shared/api";

export interface AuthResponse {
    access: JwtToken;
}
