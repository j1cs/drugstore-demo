import { User } from './user.entity';

describe('user class', () => {
  it('should make a user with no fields', () => {
    const user = new User();
    expect(user).toBeTruthy();
    expect(user.name).toBe(undefined);
    expect(user.age).toBe(undefined);
  });
  it('should make a user with name only', () => {
    const user = new User('Test');
    expect(user).toBeTruthy();
    expect(user.name).toBe('Test');
    expect(user.age).toBe(undefined);
  });
  it('should make a user with name breed and age', () => {
    const user = new User('Test', 4);
    expect(user).toBeTruthy();
    expect(user.name).toBe('Test');
    expect(user.age).toBe(4);
  });
});
