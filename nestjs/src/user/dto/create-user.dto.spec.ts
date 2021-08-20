import { CreateUserDto } from './create-user.dto';

describe('user class', () => {
  it('should make a user with no fields', () => {
    const createUserDto = new CreateUserDto();
    expect(createUserDto).toBeTruthy();
    expect(createUserDto.name).toBe(undefined);
    expect(createUserDto.age).toBe(undefined);
  });
  it('should make a user with name only', () => {
    const createUserDto = new CreateUserDto('Test');
    expect(createUserDto).toBeTruthy();
    expect(createUserDto.name).toBe('Test');
    expect(createUserDto.age).toBe(undefined);
  });
  it('should make a user with name breed and age', () => {
    const createUserDto = new CreateUserDto('Test', 4);
    expect(createUserDto).toBeTruthy();
    expect(createUserDto.name).toBe('Test');
    expect(createUserDto.age).toBe(4);
  });
});
