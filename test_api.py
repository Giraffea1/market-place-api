#!/usr/bin/env python3
"""
Market Place API Test Script
Uses existing seed data user (seller_bob) for authentication
"""

import requests
import json
import time
import sys
from typing import Dict, Any, Optional
from dataclasses import dataclass
from colorama import init, Fore, Style

# Initialize colorama for cross-platform colored output
init()

@dataclass
class TestResult:
    """Test result data class"""
    name: str
    success: bool
    status_code: int
    expected_status: int
    response_body: str
    error_message: Optional[str] = None

class APITester:
    """Main API testing class"""
    
    def __init__(self, base_url: str = "http://localhost:8080"):
        self.base_url = base_url
        self.session = requests.Session()
        self.jwt_token = None
        self.user_id = None
        self.post_id = None
        self.message_id = None
        
        # Use existing seed data user
        self.test_user = "seller_bob"
        self.test_password = "password123"  # This is the hashed password from seed data
        
        # Other users from seed data for messaging tests
        self.other_user_id = 1  # john_doe
        self.other_user_id_2 = 2  # jane_smith
        
        # Test results
        self.test_results = []
    
    def log_info(self, message: str):
        """Print info message"""
        print(f"{Fore.BLUE}[INFO]{Style.RESET_ALL} {message}")
    
    def log_success(self, message: str):
        """Print success message"""
        print(f"{Fore.GREEN}[SUCCESS]{Style.RESET_ALL} {message}")
    
    def log_error(self, message: str):
        """Print error message"""
        print(f"{Fore.RED}[ERROR]{Style.RESET_ALL} {message}")
    
    def log_warning(self, message: str):
        """Print warning message"""
        print(f"{Fore.YELLOW}[WARNING]{Style.RESET_ALL} {message}")
    
    def make_request(self, method: str, endpoint: str, data: Dict = None, 
                    expected_status: int = 200, description: str = "", 
                    requires_auth: bool = True) -> TestResult:
        """Make HTTP request and return test result"""
        
        url = f"{self.base_url}{endpoint}"
        headers = {"Content-Type": "application/json"}
        
        if requires_auth and self.jwt_token:
            headers["Authorization"] = f"Bearer {self.jwt_token}"
        
        try:
            if method.upper() == "GET":
                response = self.session.get(url, headers=headers)
            elif method.upper() == "POST":
                response = self.session.post(url, headers=headers, json=data)
            elif method.upper() == "PUT":
                response = self.session.put(url, headers=headers, json=data)
            elif method.upper() == "DELETE":
                response = self.session.delete(url, headers=headers)
            else:
                raise ValueError(f"Unsupported HTTP method: {method}")
            
            success = response.status_code == expected_status
            result = TestResult(
                name=description,
                success=success,
                status_code=response.status_code,
                expected_status=expected_status,
                response_body=response.text
            )
            
            if success:
                self.log_success(f"{description} - Status: {response.status_code}")
                if response.text:
                    try:
                        print(json.dumps(response.json(), indent=2))
                    except:
                        print(response.text)
            else:
                self.log_error(f"{description} - Expected: {expected_status}, Got: {response.status_code}")
                if response.text:
                    print(response.text)
            
            print("-" * 40)
            return result
            
        except Exception as e:
            result = TestResult(
                name=description,
                success=False,
                status_code=0,
                expected_status=expected_status,
                response_body="",
                error_message=str(e)
            )
            self.log_error(f"{description} - Exception: {e}")
            print("-" * 40)
            return result
    
    def make_public_request(self, method: str, endpoint: str, data: Dict = None, 
                          expected_status: int = 200, description: str = "") -> TestResult:
        """Make HTTP request without authentication"""
        return self.make_request(method, endpoint, data, expected_status, description, requires_auth=False)
    
    def extract_value(self, response_body: str, key: str) -> Any:
        """Extract value from JSON response"""
        try:
            data = json.loads(response_body)
            keys = key.split('.')
            value = data
            for k in keys:
                value = value[k]
            return value
        except:
            return None
    
    def test_user_login(self):
        """Test user login with existing seed data user"""
        self.log_info("=== USER LOGIN TESTS ===")
        
        # Test 1: Login with existing user (seller_bob)
        login_data = {
            "username": self.test_user,
            "password": self.test_password
        }
        
        result = self.make_public_request("POST", "/api/auth/login", login_data, 
                                        expected_status=200, description="Login with existing user (seller_bob)")
        
        if result.success:
            self.jwt_token = self.extract_value(result.response_body, "token")
            self.user_id = self.extract_value(result.response_body, "id")
            self.log_info(f"JWT Token: {self.jwt_token[:20]}...")
            self.log_info(f"User ID: {self.user_id}")
        else:
            self.log_error("Failed to login with existing user. Check if the user exists in seed data.")
            # Set default values for testing
            self.user_id = 4  # seller_bob's ID from seed data
            self.log_info(f"Using hardcoded user ID: {self.user_id} for seller_bob")
            self.log_warning("JWT token not available - some authenticated tests may fail")
        
        # Test 2: Login with wrong password
        wrong_login_data = {
            "username": self.test_user,
            "password": "wrongpassword"
        }
        
        self.make_public_request("POST", "/api/auth/login", wrong_login_data, 
                               expected_status=400, description="Login with wrong password")
        
        return True
    
    def test_user_registration(self):
        """Test user registration with new user"""
        self.log_info("=== USER REGISTRATION TESTS ===")
        
        # Test 1: Register new user
        timestamp = int(time.time())
        new_user = f"testuser_{timestamp}"
        new_email = f"test_{timestamp}@example.com"
        
        register_data = {
            "username": new_user,
            "email": new_email,
            "password": "password123",
            "firstName": "Test",
            "lastName": "User",
            "phoneNumber": "123-456-7890",
            "role": "User"
        }
        
        result = self.make_public_request("POST", "/api/auth/register", register_data, 
                                        expected_status=200, description="Register new user")
        
        if result.success:
            self.log_info(f"Successfully registered new user: {new_user}")
        
        # Test 2: Register with invalid data
        invalid_register_data = {
            "username": "test",
            "email": "invalid-email",
            "password": "short"
        }
        
        self.make_public_request("POST", "/api/auth/register", invalid_register_data, 
                               expected_status=400, description="Register with invalid data")
    
    def test_user_profile(self):
        """Test user profile management"""
        self.log_info("=== USER PROFILE TESTS ===")
        
        if not self.jwt_token:
            self.log_warning("Skipping profile tests - no JWT token available")
            return
        
        # Test 1: Get user profile
        self.make_request("GET", "/api/users/profile", description="Get user profile")
        
        # Test 2: Update user profile
        update_data = {
            "firstName": "Updated",
            "lastName": "Name",
            "email": "updated_bob@example.com",
            "phoneNumber": "987-654-3210"
        }
        
        self.make_request("PUT", "/api/users/profile", update_data, description="Update user profile")
        
        # Test 3: Get user profile after update
        self.make_request("GET", "/api/users/profile", description="Get user profile after update")
    
    def test_post_management(self):
        """Test post management functionality"""
        self.log_info("=== POST MANAGEMENT TESTS ===")
        
        if not self.jwt_token:
            self.log_warning("Skipping post management tests - no JWT token available")
            return
        
        # Test 1: Create new post
        create_post_data = {
            "title": "Test iPhone 15",
            "description": "Brand new iPhone 15, 128GB, Blue - Test post from seller_bob",
            "tags": ["electronics", "phone", "apple", "iphone", "test"],
            "askingPrice": 799.99
        }
        
        result = self.make_request("POST", "/api/posts", create_post_data, 
                                 description="Create new post")
        
        if result.success:
            self.post_id = self.extract_value(result.response_body, "id")
            self.log_info(f"Post ID: {self.post_id}")
        else:
            self.log_error("Failed to create post. This will affect messaging tests.")
            # Use an existing post ID from seed data for messaging tests
            self.post_id = 1  # iPhone 13 Pro Max from seed data
            self.log_info(f"Using existing post ID: {self.post_id} for messaging tests")
        
        # Test 2: Get specific post
        if self.post_id:
            self.make_request("GET", f"/api/posts/{self.post_id}", description="Get specific post")
        
        # Test 3: Update post (only if we created a new post)
        if result.success and self.post_id:
            update_post_data = {
                "title": "Updated Test iPhone 15",
                "description": "Updated description - Brand new iPhone 15, 128GB, Blue",
                "tags": ["electronics", "phone", "apple", "iphone", "test", "updated"],
                "askingPrice": 749.99
            }
            
            self.make_request("PUT", f"/api/posts/{self.post_id}", update_post_data, 
                            description="Update post")
        
        # Test 4: Get post after update
        if self.post_id:
            self.make_request("GET", f"/api/posts/{self.post_id}", description="Get post after update")
    
    def test_post_search(self):
        """Test post search functionality"""
        self.log_info("=== POST SEARCH TESTS ===")
        
        # Test 1: Get recent posts
        self.make_public_request("GET", "/api/posts?page=0&size=5", 
                               description="Get recent posts (public)")
        
        # Test 2: Search posts by term
        self.make_public_request("GET", "/api/posts/search?searchTerm=iPhone&page=0&size=5", 
                               description="Search posts by term")
        
        # Test 3: Search posts by tags
        self.make_public_request("GET", "/api/posts/search?tags=electronics,apple&page=0&size=5", 
                               description="Search posts by tags")
        
        # Test 4: Get posts by user (seller_bob)
        if self.user_id:
            self.make_public_request("GET", f"/api/posts/user/{self.user_id}?page=0&size=5", 
                                   description="Get posts by user (public view)")
    
    def test_user_posts(self):
        """Test user posts (owner view)"""
        self.log_info("=== USER POSTS TESTS ===")
        
        if not self.jwt_token:
            self.log_warning("Skipping user posts tests - no JWT token available")
            return
        
        # Test 1: Get my posts (owner view)
        self.make_request("GET", "/api/users/posts?page=0&size=10", 
                         description="Get my posts (owner view)")
    
    def test_messaging(self):
        """Test messaging functionality"""
        self.log_info("=== MESSAGING TESTS ===")
        
        if not self.jwt_token:
            self.log_warning("Skipping messaging tests - no JWT token available")
            return
        
        # Ensure we have a post_id and user_id for messaging tests
        if not self.post_id:
            self.post_id = 1  # Use existing post from seed data
            self.log_info(f"Using existing post ID: {self.post_id} for messaging tests")
        
        if not self.user_id:
            self.log_error("Missing user_id for messaging tests")
            return
        
        # Test 1: Send message to another user (john_doe - user ID 1)
        self.log_info(f"Sending message from seller_bob (ID: {self.user_id}) to john_doe (ID: {self.other_user_id})")
        send_message_data = {
            "postId": self.post_id,
            "receiverID": self.other_user_id,  # Send to john_doe, not to self
            "content": "Hi! I'm interested in your iPhone. Is it still available?"
        }
        
        result = self.make_request("POST", "/api/messages", send_message_data, 
                                 description="Send message to another user")
        
        if result.success:
            self.message_id = self.extract_value(result.response_body, "id")
            self.log_info(f"Message ID: {self.message_id}")
        
        # Test 2: Send another message to a different user (jane_smith - user ID 2)
        send_message_data_2 = {
            "postId": self.post_id,
            "receiverID": self.other_user_id_2,  # Send to jane_smith
            "content": "Hello! I saw your gaming laptop. Is it still for sale?"
        }
        
        self.make_request("POST", "/api/messages", send_message_data_2, 
                         description="Send message to another user (jane_smith)")
        
        # Test 3: Get messages by post (should show messages sent by seller_bob)
        self.make_request("GET", f"/api/messages/posts/{self.post_id}", 
                         description="Get messages by post")
        
        # Test 4: Get conversation between seller_bob and john_doe
        self.make_request("GET", f"/api/messages/post/{self.post_id}/conversation/{self.other_user_id}", 
                         description="Get conversation between seller_bob and john_doe")
        
        # Test 5: Try to send message to self (should fail)
        self.log_info("Testing: Send message to self (should fail)")
        send_message_to_self_data = {
            "postId": self.post_id,
            "receiverID": self.user_id,  # Try to send to self
            "content": "This should fail - sending message to self"
        }
        
        # This should return an error (400 or 403)
        self.make_request("POST", "/api/messages", send_message_to_self_data, 
                         description="Send message to self (should fail)", 
                         expected_status=400)  # or 403, depending on your validation
    
    def test_authentication(self):
        """Test authentication edge cases"""
        self.log_info("=== AUTHENTICATION TESTS ===")
        
        # Test 1: Access protected endpoint without token
        # Create a new session without the JWT token
        temp_session = requests.Session()
        try:
            response = temp_session.get(f"{self.base_url}/api/users/profile", 
                                      headers={"Content-Type": "application/json"})
            if response.status_code in [401, 403]:  # Accept both 401 and 403 as valid
                self.log_success(f"Access protected endpoint without token - Status: {response.status_code}")
            else:
                self.log_error(f"Access protected endpoint without token - Expected: 401/403, Got: {response.status_code}")
                print(response.text)
        except Exception as e:
            self.log_error(f"Exception in authentication test: {e}")
        
        print("-" * 40)
        
        # Test 2: Access protected endpoint with invalid token
        try:
            response = temp_session.get(f"{self.base_url}/api/users/profile", 
                                      headers={
                                          "Content-Type": "application/json",
                                          "Authorization": "Bearer invalid_token_here"
                                      })
            if response.status_code in [401, 403]:  # Accept both 401 and 403 as valid
                self.log_success(f"Access protected endpoint with invalid token - Status: {response.status_code}")
            else:
                self.log_error(f"Access protected endpoint with invalid token - Expected: 401/403, Got: {response.status_code}")
                print(response.text)
        except Exception as e:
            self.log_error(f"Exception in authentication test: {e}")
        
        print("-" * 40)
    
    def test_existing_data(self):
        """Test with existing seed data"""
        self.log_info("=== EXISTING DATA TESTS ===")
        
        # Test 1: Get existing posts from seed data
        self.make_public_request("GET", "/api/posts?page=0&size=10", 
                               description="Get existing posts from seed data")
        
        # Test 2: Search for existing items
        self.make_public_request("GET", "/api/posts/search?searchTerm=iPhone&page=0&size=5", 
                               description="Search for existing iPhone posts")
        
        # Test 3: Get posts by existing user (john_doe - user ID 1)
        self.make_public_request("GET", "/api/posts/user/1?page=0&size=5", 
                               description="Get posts by john_doe (user ID 1)")
        
        # Test 4: Get specific existing post (iPhone 13 Pro Max - post ID 1)
        self.make_public_request("GET", "/api/posts/1", 
                               description="Get specific existing post (iPhone 13 Pro Max)")
    
    def test_cleanup(self):
        """Test cleanup operations"""
        self.log_info("=== CLEANUP TESTS ===")
        
        if not self.jwt_token:
            self.log_warning("Skipping cleanup tests - no JWT token available")
            return
        
        # Only cleanup if we created a new post (not using seed data post)
        if self.post_id and self.post_id != 1:  # Don't delete seed data post
            # Test 1: Mark post as sold
            self.make_request("PUT", f"/api/posts/{self.post_id}/sold", 
                            description="Mark post as sold")
            
            # Test 2: Delete post
            self.make_request("DELETE", f"/api/posts/{self.post_id}", 
                            description="Delete post")
        else:
            self.log_info("Skipping cleanup - using seed data post")
        
        # Note: User deletion is commented out to preserve test data
        # self.make_request("DELETE", "/api/users/profile", description="Delete user account")
    
    def run_all_tests(self):
        """Run all test suites"""
        self.log_info("Starting Market Place API Tests")
        self.log_info(f"Base URL: {self.base_url}")
        self.log_info(f"Using existing user: {self.test_user}")
        print()
        
        try:
            # First, login with existing user
            self.test_user_login()
            
            # Run all test suites
            self.test_user_registration()
            self.test_user_profile()
            self.test_post_management()
            self.test_post_search()
            self.test_user_posts()
            self.test_messaging()
            self.test_authentication()
            self.test_existing_data()
            self.test_cleanup()
            
            # Summary
            self.log_info("=== TEST SUMMARY ===")
            self.log_success("All tests completed!")
            self.log_info(f"Test User: {self.test_user}")
            self.log_info(f"User ID: {self.user_id}")
            self.log_info(f"Post ID: {self.post_id}")
            self.log_info(f"Message ID: {self.message_id}")
            if self.jwt_token:
                self.log_info(f"JWT Token: {self.jwt_token[:20]}...")
            
            self.log_warning("Note: User account was not deleted to preserve test data")
            self.log_warning("You can manually delete the test user if needed")
            
            print()
            self.log_info("Test script completed successfully!")
            
        except KeyboardInterrupt:
            self.log_warning("Tests interrupted by user")
            sys.exit(1)
        except Exception as e:
            self.log_error(f"Unexpected error: {e}")
            sys.exit(1)

def main():
    """Main function"""
    # Check if requests is installed
    try:
        import requests
    except ImportError:
        print("Error: requests library is required. Install it with: pip install requests colorama")
        sys.exit(1)
    
    # Create and run tests
    tester = APITester()
    tester.run_all_tests()

if __name__ == "__main__":
    main() 