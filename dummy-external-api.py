from http.server import HTTPServer, BaseHTTPRequestHandler
import json
import random

# Sample products data
PRODUCTS = [
    {"name": "apple", "price": 400},
    {"name": "banana", "price": 300},
    {"name": "orange", "price": 250},
    {"name": "mango", "price": 500},
    {"name": "pineapple", "price": 600}
]

# Error response
ERROR_RESPONSE = {
    "error": "NoMoreProducts",
    "message": "Sorry we have no more products for you"
}

class ProductServer(BaseHTTPRequestHandler):
    def do_GET(self):
        if self.path == '/product':
            is_failure = random.random() >= 0.3
            # Set response headers
            self.send_response(200 if not is_failure else 404)
            self.send_header('Content-type', 'application/json')
            self.end_headers()
            
            # 30% chance of error response
            if is_failure:
                response = ERROR_RESPONSE
            else:
                response = random.choice(PRODUCTS)
            
            # Send response
            self.wfile.write(json.dumps(response).encode())
        else:
            # Handle invalid paths
            self.send_response(404)
            self.send_header('Content-type', 'application/json')
            self.end_headers()
            self.wfile.write(json.dumps({
                "error": "NotFound",
                "message": "Invalid endpoint"
            }).encode())
    
    def log_message(self, format, *args):
        # Override to provide cleaner console output
        print(f"Request: {args[0]} - Status: {args[1]}")

def run_server(port=3000):
    server_address = ('', port)
    httpd = HTTPServer(server_address, ProductServer)
    print(f"Server running at http://localhost:{port}")
    httpd.serve_forever()

if __name__ == '__main__':
    run_server()
